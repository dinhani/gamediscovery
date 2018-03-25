# ==============================================================================
# LIBRARIES
# ==============================================================================
library(stringr)
source("../../_shared/ReadWikidataRaw.r", encoding="UTF-8")
source("../../_shared/DownloadPages.r",   encoding="UTF-8")

# ==============================================================================
# READ LINKS
# ==============================================================================
games.links = ReadWikidataRaw("Game")$GameLink
games.links = str_extract(games.links, "(?<=<).+(?=>)")

# ==============================================================================
# DOWNLOAD PAGES
# ==============================================================================
DownloadPages(
  games.links,
  FUN_LINK = function(link){ link },
  FUN_FILE = function(link){ paste0(str_extract(link, "(?<=wiki/).+"), ".html") }
)
