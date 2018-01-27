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

executor.log_interval = 50
executor.reader = Reader.new(13824)
executor.saver = ZipSaver.new(options[:output])

processor = DownloadProcessor.new(executor.saver.zipper)
def processor.url(id)
  "https://www.gamecupid.com/node/#{id}/json/features"
end
def processor.filename(id)
  "#{id}.json"
end
executor.processor = processor

# execute
executor.execute()
