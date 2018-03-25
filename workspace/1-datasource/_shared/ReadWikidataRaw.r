library(data.table)

ReadWikidataRaw = function(entity, additionalEntity = ""){
    # generate filename
    filename = entity
    if(additionalEntity != ""){
        filename = paste0(filename, "-", additionalEntity)
    }
    filename = paste0("../../wikidata/data/raw/", filename, ".csv")

    # read file
    fread(filename)
}
