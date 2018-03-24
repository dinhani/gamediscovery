# 52.680.100 lines
echo -e "Source\tType\tTarget" > $GD_DATA_PARSED/dbpedia/article_attributes.tsv
parallel --progress --pipepart --block 100m -a $GD_DATA_RAW/dbpedia_article_attributes.ttl perl parse_article_attributes.pl >> $GD_DATA_PARSED/dbpedia/article_attributes.tsv
