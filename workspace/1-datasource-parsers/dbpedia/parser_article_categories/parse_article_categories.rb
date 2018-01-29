# 20.200.000 lines
# 247 seconds (4 minutes, 07 seconds)
# it could be 184 seconds (3 minutes 04 seconds)
# gsub in saver to fix two commas messing with PostgreSQL array is affecting performance
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
