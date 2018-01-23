require 'jbundler'

java_import java.io.BufferedReader
java_import java.io.BufferedInputStream
java_import java.io.FileInputStream
java_import java.io.InputStreamReader
java_import java.util.zip.GZIPInputStream
java_import java.util.zip.ZipFile
java_import org.apache.commons.io.IOUtils

class Reader

  def initialize(input)
    # FILES
    if input.class == String
      # GZIP
      if input.end_with? "gz"
        @reader = BufferedReader.new(InputStreamReader.new(GZIPInputStream.new(FileInputStream.new(input)), "utf-8"))
      # ZIP
      elsif input.end_with? "zip"
        @reader = ZipReader.new(input)
      # NEO4J
      elsif input.include? "MATCH"
        @reader = Neo4jReader.new(input)
      # POSTGRESQL
      elsif input.include? "SELECT"
        @reader = PgReader.new(input)
      # DIR
      elsif File.directory?(input)
        @reader = DirectoryReader.new(input)
      # CSV
      else
        @reader = BufferedReader.new(InputStreamReader.new(FileInputStream.new(input), "utf-8"))
      end
    end

    # SEQUENCE
    if input.class == Fixnum
      @reader = SequenceReader.new(input)
    end

    # HTML
    if input.class == Array
      @reader = XmlReader.new(input[0], input[1])
    end

    # read first
    @lines_read = 0
    @line = @reader.readLine()
  end

  # Indicates if the reader reached the end of input
  def finished?
    @line == nil
  end

  # Return the current read element in memory and also read the next element to memory
  def read
    # return what was read and read next
    begin
      return @line
    ensure
      @line = @reader.readLine()
      @lines_read += 1
    end
  end

  # The total number of "lines / files" read until the moment
  def lines_read
    @lines_read
  end
end

# ==============================================================================
# INTERNAL
# ==============================================================================
# READ A ZIP FILE
class ZipReader

  def initialize(filename)
    @reader = ZipFile.new(filename)
    @files = @reader.entries
  end

  def readLine
    if @files.hasMoreElements
      file = @files.nextElement

      # return filename and content
      filename = file.getName()

      # binary
      if filename.end_with? "png" or filename.end_with? "jpg" or filename.end_with? "jpeg" or filename.end_with? "gif"
        content = IOUtils.toByteArray(@reader.getInputStream(file))
      # text
      else
        content = IOUtils.toString(@reader.getInputStream(file), "UTF-8")
      end

      return filename, content
    else
      nil
    end
  end
end

# READ FORMATTED HTML FILES
class XmlReader
  require 'nokogiri'

  def initialize(filename, selector)
    # track current result
    @index = 0

    # parse
    if filename.end_with? "html"
      html_file = File.open(filename, "r")
      @data = Nokogiri::HTML(html_file).css(selector)
    else
      xml_file = java.io.FileInputStream.new(filename)
      jsoup_doc = org.jsoup.Jsoup.parse(xml_file, "UTF-8".to_java(:string), "".to_java(:string), org.jsoup.parser.Parser.xmlParser())
      @data = jsoup_doc.select(selector);
    end
  end

  def readLine
    begin
      (@index <= @data.size) ? @data[@index] : nil
    ensure
      @index += 1
    end
  end
end

# READ A SEQUENCE OF NUMBERS
class SequenceReader
  def initialize(max_limit)
    @index = 1
    @max_limit = max_limit
  end

  def readLine
    begin
      (@index < @max_limit) ? @index : nil
    ensure
      @index += 1
    end
  end
end

class Neo4jReader
  require 'neography'
  require 'jrjackson'

  def initialize(query)

    # track current result
    @index = 0

    # connect and query
    @client = Neography::Rest.new(ENV['GD_NEO4J_CONN_REST'])
    @data = @client.execute_query(query)["data"]
  end

  def readLine
    begin
      (@index < @data.size) ? @data[@index][0] : nil
    ensure
      @index += 1
    end
  end
end

class PgReader
  require 'sequel'

  def initialize(query)
    # track current result
    @index = 0

    # connect and query
    @client = Sequel.connect(ENV['GD_POSTGRESQL_CONN'], :user=>ENV['GD_POSTGRESQL_CONN_USER'], :password=>ENV['GD_POSTGRESQL_CONN_PASSWORD'])
    @data = @client.fetch(query).all
  end

  def readLine
    begin
      (@index < @data.size) ? @data[@index] : nil
    ensure
      @index += 1
    end
  end
end

class DirectoryReader
  def initialize(directory)
    @directory = directory

    # track current file
    @index = 0

    # get a list of all files in dir
    files = Dir.entries(directory)
    @data = files.select {|file| file != "." and file != ".."}
  end

  def readLine
    begin
      # check if already finished
      if @index >= @data.size
        return nil
      end

      # if not finished, return filename and content
      filename = @data[@index]
      return [filename, File.open("#{@directory}/#{filename}").read]
    ensure
      @index += 1
    end
  end
end
