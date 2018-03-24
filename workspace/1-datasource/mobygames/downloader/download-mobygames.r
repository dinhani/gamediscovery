# ==============================================================================
# LIBRARIES
# ==============================================================================
library(data.table)
library(httr)

# ==============================================================================
# READ GAMES
# ==============================================================================
games.ids = fread("../../wikidata/data/raw/Game-IDMobyGames.csv")$IDMobyGames

# ==============================================================================
# DOWNLOAD LINKS
# ==============================================================================
for(game.id in games.ids){
  
  # check if exists
  game.filename = paste0("../data/raw/", game.id, ".html")
  if(file.exists(game.filename)){
    next
  }
  
  # download page
  game.link = paste0("http://www.mobygames.com/game/", game.id)
  print(game.link)
  
  tryCatch({
    GET(game.link, write_disk(game.filename, overwrite = TRUE))  
  }, error=function(err){})
}
