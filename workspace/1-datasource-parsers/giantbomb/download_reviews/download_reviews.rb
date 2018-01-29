require './../../_shared/Common.rb'
require './../../_shared/processing/DownloadProcessor.rb'
require './../../_shared/output/ZipSaver.rb'

# ==============================================================================
# PARSE INPUT ARGS
# ==============================================================================
opt_parser = OptParser.new
options = opt_parser.parse([:output])

# ==============================================================================
# API KEY
# ==============================================================================
puts "Enter your API key:"
api_key = gets.chomp

# ==============================================================================
# CONSTANTS
# ==============================================================================
# create manager classes
executor = Executor.new

executor.log_interval = 10
executor.reader = Reader.new(48704 / 100)
executor.saver = ZipSaver.new(options[:output])

processor = DownloadProcessor.new(executor.saver.zipper)
def processor.url(offset)
  offset = offset * 100
  "http://www.giantbomb.com/api/user_reviews?format=json&offset=#{offset}&api_key=#{api_key}"
end
def processor.filename(offset)
  "user_reviews-#{offset * 100}.json"
end
executor.processor = processor

# execute
executor.execute()
