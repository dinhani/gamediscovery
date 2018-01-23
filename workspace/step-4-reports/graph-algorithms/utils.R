library(RNeo4j)
library(igraph)
library(reshape2)

# NEO4J
neo4j.query = function(query){
  # read connection data from environment
  conn_uri = Sys.getenv("GD_NEO4J_CONN_REST")
  conn_user = Sys.getenv("GD_NEO4J_CONN_USER")
  conn_password = Sys.getenv("GD_NEO4J_CONN_PASSWORD")
  
  # connect
  conn = startGraph(conn_uri, conn_user, conn_password)
  
  # query
  df = cypher(conn, query)

  return(df)
}

# MATRIX
df.to.matrix = function(df){
  # adjancency
  g = graph.data.frame(df, directed = FALSE)
  mat = get.adjacency(g, sparse=TRUE)

  # simplify
  rows = df[[1]]
  mat = mat[rownames(mat) %in% rows, ]
  mat = mat[, !colnames(mat) %in% rows]
  mat = as.matrix(mat)

  # sort
  mat = mat[ , order(colnames(mat))]

  return(mat)
}

df.to.wide = function(df){
  # concat labels
  df$c1 = sapply(X = df$c1, FUN = function(cell){ return(paste(cell, collapse = ";")) })

  # parse matrix
  df.data = dcast(df, id + c1 ~ c2, fun.aggregate = sum)
  df.data_matrix = df.data[3:ncol(df.data)]
  df.data_matrix[is.na(df.data_matrix)] = 0
  df.data_matrix[df.data_matrix != 0] = 1

  # convert data types
  for(i in 1:ncol(df.data_matrix)){
    df.data_matrix[,i] = as.numeric(df.data_matrix[,i])
  }

  # parse labels
  df.data_labels = as.factor(df.data$c1)

  # parse ids
  df.data_ids = as.factor(df.data$id)

  return(list(ids=df.data_ids, "labels" = df.data_labels, "matrix" = df.data_matrix))
}