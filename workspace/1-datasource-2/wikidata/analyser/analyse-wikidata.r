# ==============================================================================
# LIBRARIES
# ==============================================================================
library(data.table)
library(dplyr)
library(purrr)
library(stringr)

# ==============================================================================
# FUNCTIONS
# ==============================================================================
ProcessID = function(values){
  values = str_extract(values, "(?<=Q).+(?=>)")
  values = as.numeric(values)
}
ProcessLabel = function(values){
  values = gsub("@en", "", values)
  values = gsub("\"", "", values)
}
ProcessDataset = function(filename){
  # log
  print(filename)

  # process dataset
  df = fread(filename, colClasses = "character") %>%
    rename_(
      Target      = names(.)[3],
      TargetLabel = names(.)[4]
    ) %>%
    mutate(
      Game        = ProcessID(Game),
      GameLabel   = ProcessLabel(GameLabel),
      Target      = ProcessID(Target),
      TargetLabel = ProcessLabel(TargetLabel)
    ) %>%
    group_by(Game) %>%
    summarise(
      Target = list(TargetLabel)
    )

    # rename columns
    source = str_extract(filename, "(?<=data/).+(?=-.*.csv)")
    type   = str_extract(filename, "(?<=-).+(?=.csv)")
    colnames(df) = c(source, type)

    return(df)
}


# ==============================================================================
# ANALISYS
# ==============================================================================
# read all games
games.all = fread("../data/raw/Game.csv") %>%
  mutate(
    Game      = ProcessID(Game),
    GameLabel = ProcessLabel(GameLabel)
  )

# read all games relationships
games.files = list.files("../data/raw", pattern = "Game-", full.names = TRUE)
games.relationships = games.relationships = lapply(games.files, ProcessDataset)

# join games and relationships
games.relationships = append(games.relationships, list(games.all), 0)
View(reduce(games.relationships, left_join, by="Game"))
