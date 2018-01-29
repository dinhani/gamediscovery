# 24600 files
# 304 seconds (5 minutes 4 seconds)
# it is taking longer than this. needs investigation.

require './../../_shared/Common.rb'
require './Processor.rb'
require './Saver.rb'

# =============================================================================
# PARSE INPUT ARGS
# =============================================================================
opt_parser = OptParser.new
options = opt_parser.parse([:input, :output, :threads])

# =============================================================================
# EXECUTE PROCESS
# =============================================================================
# create manager classes
executor = Executor.new
executor.threads(options[:threads])

executor.log_interval = 50
executor.reader = Reader.new(options[:input])
executor.processor = Processor.new
executor.saver = Saver.new(options[:output])

# executes
executor.execute()
