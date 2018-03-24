DownloadRelationships = function(entityPrefix, entityCondition, additionalEntityPrefix = "", additionalEntityCondition = ""){
  # generate filename
  filename = entityPrefix
  if(additionalEntityPrefix != ""){
    filename = paste0(filename, "-", additionalEntityPrefix)
  }

  # download and save
  QueryWikidata(entityPrefix, entityCondition, additionalEntityPrefix, additionalEntityCondition) %>%
    SaveWikidata(filename)
}
