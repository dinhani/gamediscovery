BEGIN {
    printHeader()
}

{
    # parse target
    match($3, /.*Category:(.+)>/, target)

    # parse source only if it has target
    if(target[1]){
        # parse source only if source line changed
        if($1 != previous_line){
            match($1, /.*Category:(.+)>/, source)
            previous_line = $1
            previous_source_id = source[1]
        # reuse source from previous line
        } else {
            source_id = previous_source_id
        }

        # output
        print id(source_id), "category", id(target[1])
    }
}
