require './../../_shared/Common.rb'
require 'unirest'

# =============================================================================
# DOWNLOAD / SAVE function
# =============================================================================
def download(options, collection, offset)
  # log
  url = "https://igdbcom-internet-game-database-v1.p.mashape.com/#{collection}/?fields=*&offset=#{offset}&limit=50"
  puts
  puts collection
  puts "Downloading: #{url}"

  # download
  response = Unirest.get url, headers: {"X-Mashape-Key" => "jBtas95Xugmsh2940N9MBLJvK7dop15bMeJjsn2cr57fegSgir"}

  # save file
  File.write("#{options[:output]}/#{collection}_#{offset}.json", response.raw_body )

  # calculate offset to check if need to download more items
  items_retrieved = response.body.size
  if items_retrieved == 50
    offset += 50
    download(options, collection, offset)
  end
end

# =============================================================================
# PARSE INPUT ARGS
# =============================================================================
opt_parser = OptParser.new
options = opt_parser.parse([:output])

# =============================================================================
# EXECUTE PROCESS
# =============================================================================
# download pages
download(options, "themes", 0)
download(options, "genres", 0)
download(options, "game_modes", 0)
download(options, "player_perspectives", 0)
download(options, "platforms", 0)
download(options, "companies", 0)
download(options, "franchises", 0)
download(options, "keywords", 0)
#download(options, "games", 0) # API limits at 10000
#download(options, "people", 0) # API limits at 10000
