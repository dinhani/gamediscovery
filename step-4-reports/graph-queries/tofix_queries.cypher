// High rated games without series
MATCH (game:Game) OPTIONAL MATCH (game:Game)-[rseries]-(series:Series) OPTIONAL MATCH (game:Game)-[rscore]-(score:Score) 
WITH game, series, score
WHERE series IS NULL AND score IS NOT null 
AND (score.uid IN ['score-ign_9.0','score-ign_9.1','score-ign_9.2','score-ign_9.3','score-ign_9.4','score-ign_9.5','score-ign_9.6','score-ign_9.7','score-ign_9.8','score-ign_9.9','score-ign_10.0']
OR score.uid IN ['score-gamespot_9.0','score-gamespot_9.1','score-gamespot_9.2','score-gamespot_9.3','score-gamespot_9.4','score-gamespot_9.5','score-gamespot_9.6','score-gamespot_9.7','score-gamespot_9.8','score-gamespot_9.9','score-gamespot_10.0'])
RETURN DISTINCT game.uid, game.name
ORDER BY game.name

// generic query
MATCH (g:Game) 
OPTIONAL MATCH (g)--(c:Genre)
WITH g, c
WHERE c IS NULL
RETURN DISTINCT g.uid, g.name
ORDER BY g.name