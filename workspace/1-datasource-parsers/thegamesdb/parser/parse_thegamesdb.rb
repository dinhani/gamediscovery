# 33000 files
# 19 seconds

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
# create managers
executor = Executor.new
executor.threads(options[:threads])

executor.reader = Reader.new(options[:input])
executor.processor = Processor.new
executor.saver = Saver.new(options[:output])
executor.log_interval = 100

# process input file
executor.execute()
