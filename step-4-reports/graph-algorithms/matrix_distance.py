# =============================================================================
# MATRIX DISTANCE
# =============================================================================
import time
import numpy
import pandas
import scipy.io
import scipy.spatial.distance

# read graph
print("Reading")
df = pandas.read_csv("data/matrix_distance_input.csv", header = 0, index_col = 0)
m = df.as_matrix().astype('uint16')
print(m.shape)
print(m.dtype)

# calculate distance
print("Calculating")
start_time = time.time()
dm = scipy.spatial.distance.pdist(m, metric = "cosine")
dm = dm.astype('float16')
print(dm.dtype)
print("%s seconds" % (time.time() - start_time))

# transform results
print("Squaring")
dm = scipy.spatial.distance.squareform(dm)
print(dm.dtype)
print("%s seconds" % (time.time() - start_time))
print(dm.shape)
print(dm.sum())

# print
for index, row in enumerate(dm):
    source = df.index.values[index]
    targets = numpy.argsort(row)[0:5]
    for target_index in targets:
        target = df.index.values[target_index]
        target_value = row[target_index]
        
        print(source.ljust(40) + " -> " + target.ljust(40) + " = " + str(target_value))
    print("")
    input("")
