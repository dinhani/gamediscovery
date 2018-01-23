# =============================================================================
# MATRIX DISTANCE - GENERATION
# =============================================================================
library(data.table)
source("utils.r")

# get data
df = neo4j.query('MATCH (c1:Game)-[r]-(c2) WHERE c2:Genre OR c2:Theme OR c2:Atmosphere OR c2:Mechanics OR c2:Graphics OR c2:Duration OR c2:ContentCharacteristic OR c2:ContentClassification OR c2:Character OR c2:Weapon OR c2:Vehicle OR c2:Organization OR c2:Location OR c2:Period OR c2:Soundtrack OR c2:Company OR c2:Person OR c2:Platform RETURN c1.uid as c1, c2.uid as c2, r.weight as w ORDER BY c1.uid')
df = neo4j.query('MATCH (c1:Genre)--(g:Game)--(c2:Theme) RETURN c1.uid as c1, c2.uid as c2 ORDER BY c1.uid')
mat = df.to.matrix(df)

# write data with headers
fwrite(data.frame(rownames(mat), mat), "data/matrix_distance_input.csv")

# write data without headers
fwrite(as.data.frame(mat), "data/matrix_distance_input.csv", col.names = FALSE)
fwrite(data.frame(1:nrow(mat), rownames(mat)), "data/matrix_distance_games.csv", col.names = FALSE)
