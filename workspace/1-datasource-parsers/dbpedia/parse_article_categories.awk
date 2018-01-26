BEGIN {
    printHeader()
}

{
    # parse target
    match($3, /.*Category:(.+)>/, target)

    # parse source only if it has target
    if(target[1]){
        # parse source
        match($1, /.*resource\/(.+)>/, source)

        # output
        print id(source[1]), "category", id(target[1])
    }
}
