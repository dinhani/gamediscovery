// Games with most relationships
MATCH (g:Game)-[r]-(c) RETURN g.uid, g.name, count(*) ORDER BY count(*) DESC LIMIT 100

// Platforms with more games
MATCH (p:Platform)-[r]-(c) RETURN p.uid, p.name, count(*) ORDER BY count(*) DESC

// temp
match (n:Game)-[r]-(m) where n.uid = 'game-borderlands_2' or n.uid = 'game-fallout_3' return n, r, m