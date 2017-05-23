require 'jbundler'
require 'jrjackson'

java_import 'java.io.File'
java_import 'java.nio.file.Paths'

java_import 'org.apache.commons.io.FileUtils'

java_import 'org.neo4j.graphdb.factory.GraphDatabaseFactory'

java_import 'org.apache.lucene.document.Document'
java_import 'org.apache.lucene.document.Field'
java_import 'org.apache.lucene.document.Field'
java_import 'org.apache.lucene.document.FloatDocValuesField'
java_import 'org.apache.lucene.document.StringField'
java_import 'org.apache.lucene.document.TextField'
java_import 'org.apache.lucene.index.IndexWriter'
java_import 'org.apache.lucene.index.IndexWriterConfig'
java_import 'org.apache.lucene.analysis.standard.StandardAnalyzer'
java_import 'org.apache.lucene.store.SimpleFSDirectory'

# ==============================================================================
# CONSTANTS
# ==============================================================================
LUCENE_INDEX_FOLDER = "../../src/main/resources/lucene"

# ==============================================================================
# FUNCTIONS
# ==============================================================================
def reset_index
    # analyser
    analyzer = StandardAnalyzer.new

    # remove index directory
    luceneDirectory = File.new(LUCENE_INDEX_FOLDER)
    FileUtils.deleteQuietly(luceneDirectory)

    # recreate current directory
    luceneDirectory.mkdirs()
    dir = SimpleFSDirectory.new(Paths.get(LUCENE_INDEX_FOLDER))

    # create index writer
    config = IndexWriterConfig.new(analyzer)
    return IndexWriter.new(dir, config)
end

def add_document(index_writer, row)
    doc = Document.new

    # 1) BASIC FIELDS
    uid = row.get("uid")
    name = row.get("name")
    image = row.get("image")
    label = row.get("label")
    label = row.get("label_2") if label == "ConceptEntry"
    aliase = row.get("alias") unless row.get("alias").nil?
    aliase = "" if aliase.nil?
    numberOfGames = row.get("games")

    puts uid

    # 2) PROCESSED FIELDS
    # 2.1) search
    name_without_special_chars = ""
    if name.include?("'")
        name_without_special_chars = name.gsub("'", "")
    end
    search = label.to_s + " " + name.to_s + " " + aliase.to_s + " " + name_without_special_chars.to_s

    # 2.2) platforms
    platforms = row.get("platforms")

    # 3) INDEX FIELDS
    # 3.1) DOCUMENT BOOST
    boost = 1 + (numberOfGames / 500.0)

    # 3.2) COMMON FIELDS
    doc.add(StringField.new("uid", uid, Field::Store::YES))
    doc.add(StringField.new("name", name, Field::Store::YES))
    doc.add(StringField.new("alias", aliase, Field::Store::YES))
    if not image.nil? and not image.empty?
        doc.add(StringField.new("image", image, Field::Store::YES))
    end
    if platforms.size > 0
        doc.add(StringField.new("platforms", platforms.join(" "), Field::Store::YES))
    end
    doc.add(StringField.new("category", label.downcase, Field::Store::YES))

    # 3.4) BOOST FIELD
    doc.add(FloatDocValuesField.new("boost", boost))

    # 3.4) SEARCH FIELD
    searchField = TextField.new("search", search, Field::Store::YES)
    searchField.setBoost(boost)
    doc.add(searchField)

    # 4) ADD DOCUMENT
    index_writer.addDocument(doc)
end


# ==============================================================================
# EXECUTION
# ==============================================================================
puts "1) Removing current index"
index_writer = reset_index()

puts "2) Retrieving data from Neo4J"
query = "
    MATCH (n:ConceptEntry)
    OPTIONAL MATCH (n)-[]-(g:Game)
    OPTIONAL MATCH (n)<-[:PUBLISHES]-(p:Platform)
    RETURN n.uid as uid, n.name as name, n.alias as alias, n.image as image, labels(n)[1] as label, labels(n)[0] as label_2, COUNT(g) as games, COLLECT(p.name) as platforms"

neo4j = GraphDatabaseFactory
    .new
    .newEmbeddedDatabaseBuilder(File.new(ENV['GD_DATA_NEO4J']))
    .newGraphDatabase();

results = neo4j.execute(query)

puts "3) Recreating index"
results.each do |row|
    add_document(index_writer, row)
end

puts "4) Closing index and Neo4J"
index_writer.close()
neo4j.shutdown()
