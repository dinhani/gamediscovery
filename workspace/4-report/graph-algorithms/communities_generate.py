import utils
import csv

# query
results = utils.neo4j_query("MATCH (c1:Game)-[]-(c2:Genre) RETURN c1.uid as c1, c2.uid as c2 ORDER BY c1.uid")

# write csv
with open('data/communities.csv', 'w') as csvfile:
    writer = csv.writer(csvfile, delimiter=' ', lineterminator='\n')    
    for row in results:        
        writer.writerow([row['c1'], row['c2']])