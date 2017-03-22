# =============================================================================
# PCA
# =============================================================================
source("utils.r")

# get data
df = neo4j.query("MATCH (c1:Graphics)--(g:Game)--(c2) WHERE c2:Platform WITH id(g) as id, c1.name as c1, c2.name as c2 ORDER BY id, c1.name RETURN id, COLLECT(c1) as c1, c2 ORDER BY id")
df.wide = df.to.wide(df)
df.data = df.wide$matrix

# calculate PCA
pca = prcomp(df.data, center = TRUE, scale = FALSE)
plot(pca, type = "l")
plot(pca$x[,1], pca$x[,2])