# 20.200.000 lines
echo -e "source\tcategory\ttarget" > $GD_DATA_PARSED/dbpedia/article_categories.tsv
perl -n parse_article_categories.pl < $GD_DATA_RAW/dbpedia_article_categories.ttl >> $GD_DATA_PARSED/dbpedia/article_categories.tsv
