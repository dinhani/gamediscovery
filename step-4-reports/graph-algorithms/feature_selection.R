# =============================================================================
# FEATURE SELECTION
# =============================================================================
library(Boruta)
library(FSelector)

library(caret)
source("utils.r")

# read from neo4j
df = neo4j.query("MATCH (c1:Graphics)--(g:Game)--(c2) WHERE c2:Genre OR c2:Platform WITH id(g) as id, c1.name as c1, c2.name as c2 ORDER BY id, c1.name RETURN id, COLLECT(c1) as c1, c2 ORDER BY id")
df.wide = df.to.wide(df)
df.labels = df.wide$labels
df.data = df.wide$matrix

# correlation
cor_mat = cor(df.data)
findCorrelation(cor_mat, cutoff=0.5)

boruta.train = Boruta(df.labels ~ ., data = df.data, doTrace = 2)
final.boruta <- TentativeRoughFix(boruta.train)
getSelectedAttributes(boruta.train, withTentative = F)
print(boruta.train)