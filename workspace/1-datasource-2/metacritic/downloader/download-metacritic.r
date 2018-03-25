# ==============================================================================
# LIBRARIES
# ==============================================================================
source("../../_shared/ReadWikidataRaw.r", encoding="UTF-8")
source("../../_shared/DownloadPages.r", encoding="UTF-8")

# ==============================================================================
# READ IDS
# ==============================================================================
games.ids = ReadWikidataRaw("Game", "Metacritic")$Metacritic

# ==============================================================================
# DOWNLOAD PAGES
# ==============================================================================
DownloadPages(
  games.ids,
  FUN_LINK = function(id){ paste0("http://www.metacritic.com/", id) },
  FUN_FILE = function(id){ paste0(gsub("/", "__", id), ".html") }
)
