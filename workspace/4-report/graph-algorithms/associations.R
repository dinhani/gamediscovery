# =============================================================================
# ASSOCIATION
# =============================================================================
library(arules)
library(arulesViz)
source("utils.r")

# read from neo4j
df = neo4j.query("MATCH (g:Game)--(c:Mechanics) RETURN g.name as game, c.name as c ORDER BY g.name")
write.table(df, "data/associations.csv", sep=",", col.names = FALSE, row.names = FALSE, quote = FALSE)

# transactions
transactions = read.transactions("data/associations.csv", format="single", sep=",", cols=c(1, 2))

# apriori
rules = apriori(transactions, parameter=list(supp = 0.001, conf = 0.7, minlen=2, maxlen=2))
inspect(sort(rules, by="lift"))
plot(rules, method="graph")

# eclat
rules = eclat(transactions, parameter=list(supp = 0.001, minlen=2, maxlen=2))
inspect(sort(rules, by="support"))
plot(rules)