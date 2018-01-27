require './../../_shared/Common.rb'
require './../../_shared/processing/DownloadProcessor.rb'
require './../../_shared/output/ZipSaver.rb'

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
executor.threads(20)

executor.log_interval = 10
executor.reader = Reader.new([options[:input], "table tr td:last a"])
executor.saver = ZipSaver.new(options[:output])

processor = DownloadProcessor.new(executor.saver.zipper)
def processor.url(link_elemenet)
  link = link_elemenet.attr("href")
  "https://www.igdb.com/#{link}"
end
def processor.filename(link_elemenet)
  filename = link_elemenet.attr("href")
  filename = filename.rpartition("/")[-1]
  "#{filename}.html"
end
executor.processor = processor

# execute
executor.execute()
