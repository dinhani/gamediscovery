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
executor.reader = Reader.new(34868)
executor.saver = ZipSaver.new(options[:output])

processor = DownloadProcessor.new(executor.saver.zipper)
def processor.url(id)
  "http://thegamesdb.net/api/GetGame.php?id=#{id}"
end
def processor.filename(id)
  "#{id}.xml"
end

executor.processor = processor

# execute download
executor.execute()
