# 23.990.516 lines
echo -e "Source\tType\tTarget" > $GD_DATA_PARSED/dbpedia/article_categories.tsv
parallel --progress --pipepart --block 100m -a $GD_DATA_RAW/dbpedia_article_categories.ttl perl parse_article_categories.pl >> $GD_DATA_PARSED/dbpedia/article_categories.tsv
