# 250 seconds

require './../../_shared/Common.rb'
require './Processor.rb'
require './Saver.rb'

# =============================================================================
# PARSE INPUT ARGS
# =============================================================================
opt_parser = OptParser.new
options = opt_parser.parse([:input, :output, :threads])

# =============================================================================
# EXECUTE
# =============================================================================
# create manager classes
executor = Executor.new
executor.threads(options[:threads])

executor.log_interval = 1
executor.reader = Reader.new(options[:input])
executor.processor = Processor.new
executor.saver = Saver.new(options[:output])

# execute
executor.execute()
