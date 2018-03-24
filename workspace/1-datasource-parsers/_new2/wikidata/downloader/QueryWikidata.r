# ==============================================================================
# MAIN
# ==============================================================================
QueryWikidata = function(entityPrefix, entityCondition, additionalEntityPrefix = "", additionalEntityCondition = ""){
    endpoint = "https://query.wikidata.org/sparql"

    # generate SELECT
    query = "SELECT ?${entityPrefix} ?${entityPrefix}Label"
    if(additionalEntityPrefix != ""){
        query = paste(query, "?${additionalEntityPrefix} ?${additionalEntityPrefix}Label")
    }

    # generate base WHERE
    query = paste(query, "WHERE { SERVICE wikibase:label { bd:serviceParam wikibase:language 'en'. }")

    # generate WHERE conditions
    query = paste(query, "?${entityPrefix} ${entityCondition}.")
    if(additionalEntityCondition != ""){
        query = paste(query, "?${entityPrefix} ${additionalEntityCondition} ?${additionalEntityPrefix}")
    }

    # end query
    query = paste(query, "}")

    # interporlate
    query = str_interp(query)

    # do query
    print(query)
    SPARQL(endpoint, query)$results
}
