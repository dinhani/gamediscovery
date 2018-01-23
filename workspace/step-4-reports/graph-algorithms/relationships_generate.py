import utils
import csv

# query
results = utils.neo4j_query("MATCH (s:Game) MATCH (s)-[r]->(m) RETURN s.uid AS source, m.uid as target, SUM(r.weight) AS `weight`")

# write csv
with open('data/relationships.csv', 'w') as csvfile:
    writer = csv.writer(csvfile, delimiter=' ', lineterminator='\n')    
    writer.writerow(['source', 'target', 'weight'])
    for row in results:        
        writer.writerow([row['source'], row['target'], row['weight']])