# =============================================================================
# MATRIX DISTANCE - RESULTS
# =============================================================================
# read games uids
games = read.csv("data/matrix_distance_games.csv", header = F)
colnames(games) = c("index", "name")
games$name = sapply(games$name, FUN=function(g){return(gsub("game-", "", g))})

# read matrix file
f.size = file.info("data/matrix_distance_results_64shorts.bin")$size
f = file("data/matrix_distance_results_64shorts.bin", "rb")
data = readBin(f, "integer", n = f.size / 2, size = 2, signed = T, endian = "big")

# transform to dataframe
df = as.data.frame(matrix(data, ncol = 64, byrow = T))
df[] = games$name[match(unlist(df), games$index)]
rownames(df) = games$name

View(df[,1:10])
