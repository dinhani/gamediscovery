function printHeader() {
    # define separator
    OFS="\t"

    # print header
    print "source", "type", "target"
}

function id(str){
    return tolower(str)
}
