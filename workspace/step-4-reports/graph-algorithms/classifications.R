# =============================================================================
# CLASSIFICATIONS
# =============================================================================
library(reshape2)
library(data.table)
library(e1071) # svm / naive bayes
library(class) # knn
source("utils.r")

# read from neo4j
df = neo4j.query("MATCH (c1:Graphics)--(g:Game)--(c2) WHERE c2:Genre WITH id(g) as id, c1.name as c1, c2.name as c2 ORDER BY id, c1.name RETURN id, COLLECT(c1) as c1, c2 ORDER BY id")
df.wide = df.to.wide(df)
df.labels = df.wide$labels
df.data = df.wide$matrix

# save for Python
fwrite(data.frame(df.labels, df.data), "data/matrix_classification_input.csv", eol="\n", quote = TRUE)

# prepare (training and test data)
test_sample = sample(nrow(df.data), floor(nrow(df.data) * 0.75))
df.train = df.data[test_sample,]
df.train_labels = as.factor(df.labels[test_sample])
df.test = df.data[-test_sample,]
df.test_labels = as.factor(df.labels[-test_sample])

# =============================================================================
# KNN
# =============================================================================
pred = class::knn(df.train, df.test, df.train_labels, k = 1)

# =============================================================================
# NAIVE BAYES
# =============================================================================
bayes_model = naiveBayes(df.train_labels ~ ., data = df.train)
pred = predict(bayes_model, as.numeric(as.matrix(df.test)))

# =============================================================================
# SVM
# =============================================================================
svm_model = svm(formula = df.train_labels ~ ., data = df.train)
pred = predict(svm_model, df.test)

# =============================================================================
# VALIDATION
# =============================================================================
sum(pred == as.vector(df.test_labels)) / length(df.test_labels)
View(data.frame(pred, df.test_labels, as.vector(df.test_labels) == as.vector(pred)))
