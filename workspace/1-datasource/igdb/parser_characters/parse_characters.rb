# 24600 files
# 304 seconds (5 minutes 4 seconds)

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
executor.threads(4)

executor.log_interval = 20
executor.reader = Reader.new(options[:input])
executor.processor = Processor.new
executor.saver = Saver.new(options[:output])

# executes
executor.execute()
