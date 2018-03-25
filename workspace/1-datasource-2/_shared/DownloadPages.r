library(httr)

DownloadPages = function(ids, FUN_LINK, FUN_FILE){
    for(id in ids){
        # generate filename
        filename = FUN_FILE(id)
        filename = paste0("../data/raw/", filename)

        # if file exists, do not download again
        if(file.exists(filename)){
            next
        }

        # generate link
        link = FUN_LINK(id)

        # download link
        print(link)
        tryCatch({
            GET(link, write_disk(filename, overwrite=TRUE), add_headers("user-agent" = "Mozilla/5.0"))
        }, error=function(err){})
    }
}
