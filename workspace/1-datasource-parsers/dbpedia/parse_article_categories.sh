# 20.200.000 lines
echo -e "source\ttype\ttarget" > $GD_DATA_PARSED/dbpedia/article_categories.tsv
parallel --progress --pipepart --block 100m -a $GD_DATA_RAW/dbpedia_article_categories.ttl perl parse_article_categories.pl >> $GD_DATA_PARSED/dbpedia/article_categories.tsv
