# 20.200.000 lines
gawk -f ../_shared/functions.awk -f parse_article_categories.awk $GD_DATA_RAW/dbpedia_article_categories.ttl > $GD_DATA_PARSED/dbpedia/article_categories.tsv
