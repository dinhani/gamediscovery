# 17300 lines
# 2  seconds

require './../../_shared/Common.rb'
require './Processor.rb'
require './Saver.rb'

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
executor.threads(1) # avoid concurrency in saver

executor.log_interval = 100
executor.reader = Reader.new(options[:input])
executor.processor = Processor.new
executor.saver = Saver.new(options[:output])

# execute
executor.execute()
