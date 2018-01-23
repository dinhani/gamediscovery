# 4.900.000 lines
# 79 seconds (1 minute 19 seconds)
require 'jbundler'
require './../../_shared/input/OptParser.rb'
require './../../_shared/input/Reader.rb'
require './../../_shared/processing/Executor.rb'
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
# create managers
executor = Executor.new

executor.log_interval = 100000
executor.reader = Reader.new(options[:input])
executor.processor = Processor.new()
executor.saver = Saver.new(options[:output])

# process input file
executor.execute()
