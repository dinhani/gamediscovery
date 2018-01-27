# 4.900.000 lines
echo -e "source\ttype\ttarget" > $GD_DATA_PARSED/dbpedia/category_categories.tsv
parallel --progress --pipepart --block 100m -a $GD_DATA_RAW/dbpedia_categories.ttl perl parse_categories.pl >> $GD_DATA_PARSED/dbpedia/category_categories.tsv
