# ==============================================================================
# LIBRARIES
# ==============================================================================
source("../../_shared/ReadWikidataRaw.r", encoding="UTF-8")
source("../../_shared/DownloadPages.r",   encoding="UTF-8")

# ==============================================================================
# READ IDS
# ==============================================================================
games.ids = ReadWikidataRaw("Game", "MobyGames")$MobyGames

# ==============================================================================
# DOWNLOAD
# ==============================================================================
DownloadPages(
  games.ids,
  FUN_LINK = function(id){ paste0("http://www.mobygames.com/game/", id) },
  FUN_FILE = function(id){ paste0(id, ".html") }
)