require './../../_shared/Common.rb'
require './../../_shared/processing/DownloadProcessor.rb'
require './../../_shared/output/ZipSaver.rb'

# =============================================================================
# PARSE INPUT ARGS
# =============================================================================
opt_parser = OptParser.new
options = opt_parser.parse([:output])

# =============================================================================
# EXECUTE PROCESS
# =============================================================================
# create manager classes
executor = Executor.new
executor.threads(20)

executor.log_interval = 10
executor.reader = Reader.new(460)
executor.saver = ZipSaver.new(options[:output])

processor = DownloadProcessor.new(executor.saver.zipper)
def processor.url(id)
  "http://www.gamespot.com/reviews/?page=#{id}"
end
def processor.filename(id)
  "#{id}.html"
end
executor.processor = processor

# execute download
executor.execute()
