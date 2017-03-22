require './../../_shared/Common.rb'
require './../../_shared/processing/DownloadProcessor.rb'
require './../../_shared/output/ZipSaver.rb'

# =============================================================================
# PARSE INPUT ARGS
# =============================================================================
opt_parser = OptParser.new
options = opt_parser.parse([:output])

# =============================================================================
# EXECUTE PROCESS
# =============================================================================
# create manager classes
executor = Executor.new
executor.threads(70)

executor.log_interval = 50
executor.reader = Reader.new("
SELECT id, link, 'en' as lang FROM wikidata_entities WHERE attributes @> '{\"P31\":[\"Q7889\"]}'
UNION
SELECT id, link_pt, 'pt' as lang FROM wikidata_entities WHERE attributes @> '{\"P31\":[\"Q7889\"]}' AND link_pt <> ''
")
executor.saver = ZipSaver.new(options[:output])

processor = DownloadProcessor.new(executor.saver.zipper)
def processor.url(row)
  link = URI.encode(row[:link])
  if row[:lang] == 'en'
    "https://en.wikipedia.org/wiki/#{link}"
  else
    "https://pt.wikipedia.org/wiki/#{link}"
  end
end
def processor.filename(row)
  # base
  filename = row[:link]

  # remove special chars
  filename = filename.gsub("/", "-").gsub("\\", "-").gsub(":", "-").gsub(",", "-")
  filename = filename + "_" if filename.end_with? "."

  # append if pt link
  filename = "#{row[:lang]}_#{filename}"

  # filename
  "#{filename}.html"
end
executor.processor = processor

# execute
executor.execute()
