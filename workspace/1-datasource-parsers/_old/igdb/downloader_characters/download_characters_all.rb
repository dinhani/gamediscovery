require './../../_shared/Common.rb'
require './../../_shared/processing/Downloader.rb'

# =============================================================================
# PARSE INPUT ARGS
# =============================================================================
opt_parser = OptParser.new
options = opt_parser.parse([:output])

# =============================================================================
# EXECUTE PROCESS
# =============================================================================
# download pages
downloader = Downloader.new
cooperative_games = downloader.download('https://www.igdb.com/characters/')

# save page
File.write("#{options[:output]}", cooperative_games)
