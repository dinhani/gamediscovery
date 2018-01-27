# 4.900.000 lines
echo -e "source\tcategory\ttarget" > $GD_DATA_PARSED/dbpedia/category_categories.tsv
perl -n parse_categories.pl < $GD_DATA_RAW/dbpedia_categories.ttl >> $GD_DATA_PARSED/dbpedia/category_categories.tsv
