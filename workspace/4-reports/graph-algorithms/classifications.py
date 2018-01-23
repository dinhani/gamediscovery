from sklearn.preprocessing import MultiLabelBinarizer
from sklearn.cross_validation import train_test_split
from sklearn import feature_selection
from sklearn import grid_search
from sklearn import ensemble, multiclass, neighbors, svm, tree
from sklearn import metrics
import pandas as pd
from lib import corex as ce

# =============================================================================
# INPUT
# =============================================================================
print("Reading")
df = pd.read_csv("data/matrix_classification_input.csv", encoding="iso-8859-1")

# features
data = df.ix[:,1:len(df.columns)].as_matrix()

# labels
labels = [] 
for i in range(len(df.ix[:,0])):        
    labels.append(df.ix[i,0].split(";"))     
labels_binarizer = MultiLabelBinarizer()
labels = labels_binarizer.fit_transform(labels)

# =============================================================================
# CLASSIFIER
# =============================================================================
print("Classifier")
data_train, data_test, labels_train, labels_test = train_test_split(data, labels, test_size=0.25, random_state=42)
#clf = ensemble.RandomForestClassifier()
clf = neighbors.KNeighborsClassifier()
clf.fit(data_train, labels_train)  

# =============================================================================
# TEST
# =============================================================================
print("Testing")
pred = clf.predict(data_test)
pred_labels = labels_binarizer.inverse_transform(pred)

print("===============================================================================")
print("Accuracy:  ", metrics.jaccard_similarity_score(labels_test,  pred))
