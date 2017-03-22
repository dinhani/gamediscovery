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
executor.threads(10)

executor.log_interval = 10
executor.reader = Reader.new("MATCH (n:Game) RETURN DISTINCT n.name ORDER BY n.name")
executor.saver = ZipSaver.new(options[:output])

processor = DownloadProcessor.new(executor.saver.zipper)
def processor.url(name)
  id = ID::parse(name)
  "http://www.mobygames.com/game/#{id}"
end
def processor.filename(name)
  id = ID::parse(name)
  "#{id}.html"
end
executor.processor = processor

# execute
executor.execute()
