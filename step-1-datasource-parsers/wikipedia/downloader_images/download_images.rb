require './../../_shared/Common.rb'
require './../../_shared/processing/DownloadProcessor.rb'
require './../../_shared/output/ZipSaver.rb'
require 'nokogiri'


# =============================================================================
# PARSE INPUT ARGS
# =============================================================================
opt_parser = OptParser.new
options = opt_parser.parse([:input, :output])

# =============================================================================
# EXECUTE PROCESS
# =============================================================================
# create manager classes
executor = Executor.new
executor.threads(50)

executor.log_interval = 50
executor.reader = Reader.new(options[:input])
executor.saver = ZipSaver.new(options[:output])

processor = DownloadProcessor.new(executor.saver.zipper)
def processor.url(content)
  filename, html = content
  img = Nokogiri::HTML(html).at_css('.infobox .image img')

  if not img.nil?
    src = img.attr("src")
    "https:#{src}"
  else
    nil
  end
end
def processor.filename(content)
  filename, html = content

  # base name of file
  filename = filename[3..-1]

  # extension from url
  url = url(content)
  return nil if url.nil?

  "#{File.basename filename, ".*"}#{File.extname url}".downcase
end
executor.processor = processor

# execute
executor.execute()
