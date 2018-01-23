# =============================================================================
# HEATMAP
# =============================================================================
library(ggplot2)
library(reshape2)
source("utils.r")

# get data
df = neo4j.query('MATCH (c1:ReleaseYear)--(g:Game)--(c2:Genre) RETURN c1.name as c1, c2.name as c2 ORDER BY c1.name DESC, c2.name ASC')
mat = df.to.matrix(df)

# prepare data
df.m = melt(mat)
df.m = df.m[df.m$value != 0,]
names(df.m) = c("c1", "c2", "value")

# heatmap
ggplot(data=df.m, aes(x=c2, y=c1)) +
  geom_raster(aes(fill = value, width=0.95, height=0.95)) +
  scale_fill_gradient(low = "white", high = "red") +
  labs(x = "", y = "") +
  theme_classic(base_size = 10) +
  theme(axis.text.x = element_text(angle = 90, hjust = 1))

