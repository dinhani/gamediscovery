# ==============================================================================
# MAIN
# ==============================================================================
QueryWikidata = function(entityPrefix, entityCondition, additionalEntityPrefix = "", additionalEntityCondition = "", entityLink = FALSE){
    endpoint = "https://query.wikidata.org/sparql"

    # ==========================================================================
    # SELECT
    # ==========================================================================
    query = "SELECT ?${entityPrefix} ?${entityPrefix}Label"
    if(entityLink){
        query = paste(query, "?${entityPrefix}Link")
    }
    if(additionalEntityPrefix != ""){
        query = paste(query, "?${additionalEntityPrefix} ?${additionalEntityPrefix}Label ?${additionalEntityPrefix}Link")
    }

    # ==========================================================================
    # WHERE - BASE
    # ==========================================================================
    query = paste(query, "WHERE { SERVICE wikibase:label { bd:serviceParam wikibase:language 'en' . }")

    # ==========================================================================
    # WHERE - ENTITY
    # ==========================================================================
    query = paste(query, "?${entityPrefix} ${entityCondition} .")
    if(entityLink){
        query = paste(query, "OPTIONAL {  ")
        query = paste(query, "?${entityPrefix}Link schema:about ?${entityPrefix} .")
        query = paste(query, "?${entityPrefix}Link schema:inLanguage 'en' .")
        query = paste(query, "?${entityPrefix}Link schema:isPartOf <https://en.wikipedia.org/> .")
        query = paste(query, "}")
    }

    # ==========================================================================
    # WHERE - ADDITIONAL ENTITY
    # ==========================================================================
    if(additionalEntityCondition != ""){
        query = paste(query, "?${entityPrefix} ${additionalEntityCondition} ?${additionalEntityPrefix} .")
        query = paste(query, "OPTIONAL {  ")
        query = paste(query, "?${additionalEntityPrefix}Link schema:about ?${additionalEntityPrefix} .")
        query = paste(query, "?${additionalEntityPrefix}Link schema:inLanguage 'en' .")
        query = paste(query, "?${additionalEntityPrefix}Link schema:isPartOf <https://en.wikipedia.org/> .")
        query = paste(query, "}")
    }

    # ==========================================================================
    # END
    # ==========================================================================
    query = paste(query, "}")

    # ==========================================================================
    # EXECUTE
    # ==========================================================================
    query = str_interp(query)
    print(query)
    SPARQL(endpoint, query)$results
}
