require './../../_shared/Common.rb'
require './../../_shared/output/ZipSaver.rb'
require './Processor.rb'

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
executor.threads(7)

executor.log_interval = 10
executor.reader = Reader.new(options[:input])
executor.saver = ZipSaver.new(options[:output])
executor.processor = Processor.new(executor.saver.zipper)

# execute
executor.execute()
