# ==============================================================================
# LIBRARIES
# ==============================================================================
library(data.table)
library(dplyr)
library(stringr)
library(SPARQL)

# ==============================================================================
# FUNCTIONS
# ==============================================================================
source("QueryWikidata.r"        , encoding="UTF-8")
source("SaveWikidata.r"         , encoding="UTF-8")

source("DownloadRelationships.r", encoding="UTF-8")
source("DownloadGames.r"        , encoding="UTF-8")

# ==============================================================================
# DOWNLOAD - GAMES
# ==============================================================================
DownloadGames(entityLink = TRUE)

DownloadGames("Platform",     "wdt:P400")
DownloadGames("ReleaseDate",  "wdt:P577")

DownloadGames("Developer",    "wdt:P178")
DownloadGames("Publisher",    "wdt:P123")
DownloadGames("Engine",       "wdt:P408")
DownloadGames("Input",        "wdt:P479")
DownloadGames("Distribution", "wdt:P437")


DownloadGames("Award",        "wdt:P166")
DownloadGames("Review",       "wdt:P444")

DownloadGames("Artist",       "wdt:P3080")
DownloadGames("Composer",     "wdt:P86")
DownloadGames("Designer",     "wdt:P287")
DownloadGames("Director",     "wdt:P57")

DownloadGames("Genre",        "wdt:P136")
DownloadGames("GameMode",     "wdt:P404")
DownloadGames("Series",       "wdt:P179")
DownloadGames("Theme",        "wdt:P921")
DownloadGames("Based",        "wdt:P144")
DownloadGames("Inspiration",  "wdt:P941")
DownloadGames("Location",     "wdt:P840")
DownloadGames("Period",       "wdt:P2408")
DownloadGames("MinNumberPlayers", "wdt:P1872")
DownloadGames("MaxNumberPlayers", "wdt:P1873")

DownloadGames("ESRB",         "wdt:P852")
DownloadGames("PEGI",         "wdt:P908")

DownloadGames("IMDB",       "wdt:P345")
DownloadGames("Metacritic", "wdt:P1712")
DownloadGames("MobyGames",  "wdt:P1933")
DownloadGames("Steam",      "wdt:P1733")
