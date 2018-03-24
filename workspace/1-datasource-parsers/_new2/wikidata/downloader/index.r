# ==============================================================================
# BIBLIOTECAS
# ==============================================================================
library(data.table)
library(dplyr)
library(stringr)
library(SPARQL)

# ==============================================================================
# FUNÇÕES
# ==============================================================================
source("QueryWikidata.r"        , encoding="UTF-8")
source("SaveWikidata.r"         , encoding="UTF-8")

source("DownloadRelationships.r", encoding="UTF-8")
source("DownloadGames.r"        , encoding="UTF-8")

# ==============================================================================
# DOWNLOAD - GAMES
# ==============================================================================
DownloadGames()
DownloadGames("Artist",       "wdt:P3080")
DownloadGames("Based",        "wdt:P144")
DownloadGames("Composer",     "wdt:P86")
DownloadGames("Developer",    "wdt:P178")
DownloadGames("Designer",     "wdt:P287")
DownloadGames("Director",     "wdt:P57")
DownloadGames("Distribution", "wdt:P437")
DownloadGames("ESRB",         "wdt:P852")
DownloadGames("Engine",       "wdt:P404")
DownloadGames("GameMode",     "wdt:P404")
DownloadGames("Genre",        "wdt:P136")
DownloadGames("Input",        "wdt:P479")
DownloadGames("Location",     "wdt:P840")
DownloadGames("PEGI",         "wdt:P908")
DownloadGames("Period",       "wdt:P2408")
DownloadGames("Platform",     "wdt:P400")
DownloadGames("Publisher",    "wdt:P123")
DownloadGames("ReleaseDate",  "wdt:P577")
DownloadGames("Series",       "wdt:P179")
DownloadGames("Theme",        "wdt:P921")

# ==============================================================================
# DOWNLOAD - LOCATIONS
# ==============================================================================
DownloadRelationships("Country", "wdt:P31 wd:Q6256", "PartOf",    "wdt:P361")
DownloadRelationships("Country", "wdt:P31 wd:Q6256", "Continent", "wdt:P30")
DownloadRelationships("City",    "wdt:P31 wd:Q515",  "Country",   "wdt:P17")
