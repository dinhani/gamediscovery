# 6 seconds
# 42100 games

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
#create manager classes
executor = Executor.new

executor.log_interval = 100
executor.reader = Reader.new([options[:input], "Game"])
executor.processor = Processor.new
executor.saver = Saver.new(options[:output])

#execute
executor.execute()
