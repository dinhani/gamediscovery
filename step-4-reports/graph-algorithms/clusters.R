# =============================================================================
# CLUSTERING
# =============================================================================
library(lsa)
source("utils.r")

# get data
df = neo4j.query('MATCH (c1:Platform)--(g:Game)--(c2:ReleaseYear) RETURN c1.name as c1, c2.name as c2 ORDER BY g.name')
mat = df.to.matrix(df)
mat[mat > 0] = 1 # maybe

# hclust (euclidean)
dm = dist(mat)
hc = hclust(dm)
plot(hc)
image(as.matrix(dm))