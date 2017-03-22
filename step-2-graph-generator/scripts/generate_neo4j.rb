require 'fileutils'
require 'csv'
require 'timeout'


java_import 'java.lang.ProcessBuilder'
java_import 'java.lang.Runtime'

java_import 'java.util.Set'
java_import 'java.util.HashSet'

# ==============================================================================
# PATHS
# ==============================================================================
NEO4J = ENV['GD_DATA_NEO4J']
PARSED = ENV['GD_DATA_PARSED']
NODES = "#{PARSED}/neo4j/nodes/"
RELATIONSHIPS = "#{PARSED}/neo4j/relationships/"

# ==============================================================================
# FUNCTIONS
# ==============================================================================
def generate_nodes
    ids = HashSet.new

    # remove current
    FileUtils.rm_f(NODES + "nodes.csv")

    # write
    CSV.open(NODES + "nodes.csv", "w") do |csv|
        # header
        csv << ["uid:ID", "name", "alias", "image", "summary", ":LABEL"]

        # concat
        Dir.foreach(NODES) do |filename|
            next if filename == "." or filename == ".." or filename == "nodes.csv"

            # read
            puts NODES + filename
            CSV.foreach(NODES + filename, :headers => true) do |row|
                # check exists
                current_id = row['id']
                next if ids.contains(current_id)
                ids.add(current_id)

                # parse
                label = filename + ';ConceptEntry'

                # generate row
                csv << [current_id, row['name'], row['alias'], row['image'], row['summary'], label]
            end
        end
    end
end

def generate_relationships
    # remove current
    FileUtils.rm_f(RELATIONSHIPS + "relationships.csv")

    # write
    CSV.open(RELATIONSHIPS + "relationships.csv", "w") do |csv|
        # header
        csv << [":START_ID", ":END_ID", ":TYPE", "weight:double"]

        # concat
        Dir.foreach(RELATIONSHIPS) do |filename|
            next if filename == "." or filename == ".." or filename == "relationships.csv"

            # read
            puts RELATIONSHIPS + filename
            CSV.foreach(RELATIONSHIPS + filename, :headers => true) do |row|
                # generate row
                csv << [row['source'], row['target'], row['type'], row['weight']]
            end
        end
    end
end



def generate_neo4j
    # remove current
    FileUtils.rm_rf(NEO4J)

    # generate database
    puts "Creating database"
    proc = Runtime.getRuntime.exec ["neo4j-import.bat", "--into", NEO4J, "--nodes", "#{NODES}/nodes.csv", "--relationships",  "#{RELATIONSHIPS}/relationships.csv", "--ignore-empty-strings", "false"].to_java(:string)
    sleep 15
    proc.destroy()

    # create indexes and drop some nodes
    puts "Creating indexes and dropping nodes"
    proc = Runtime.getRuntime.exec ["neo4j-shell.bat", "-path", NEO4J, "-file", "neo4j_commands.txt"].to_java(:string)
    sleep 15
    proc.destroy()
end

def write_commands
    def create_index(label)
        "cypher CREATE INDEX ON :#{label}(uid);\n"
    end
    def delete_orphans()
        "cypher MATCH (n:ConceptEntry) OPTIONAL MATCH (n)-[r]-(m) WITH n, m WHERE m IS NULL DELETE n;\n"
    end
    def delete_irrelevant(label, min_value_to_keep)
        "cypher MATCH (n:#{label})-[]-(g:Game) WITH n, count(*) as c WHERE c < #{min_value_to_keep} DETACH DELETE n;\n"
    end

    # write commands
    f = File.open("neo4j_commands.txt", "w")

    # indexes
    f.write create_index("ConceptEntry")
    f.write create_index("Game")

    # orphan
    f.write delete_orphans()

    # irrelevant
    f.write delete_irrelevant("Platform", 30)
    f.write delete_irrelevant("GameMode", 30)
    f.write delete_irrelevant("Genre", 30)
    f.write delete_irrelevant("Series", 2)

    f.close
end

# ==============================================================================
# EXECUTION
# ==============================================================================
puts "1) Deleting current output files"
#generate_nodes()

puts "2) Recreating nodes.csv"
#generate_relationships()

puts "3) Writing Neo4J command to run after creation"
write_commands();

puts "4) Recreating Neo4J database"
generate_neo4j()