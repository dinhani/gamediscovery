# 4.900.000 lines
gawk -f ../_shared/functions.awk -f parse_categories.awk $GD_DATA_RAW/dbpedia_categories.ttl > $GD_DATA_PARSED/dbpedia/category_categories.tsv
