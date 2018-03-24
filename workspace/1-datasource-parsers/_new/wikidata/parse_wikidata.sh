echo -e "Source\tType\tTarget" > $GD_DATA_PARSED/wikidata/entities.tsv
gzip -d -c $GD_DATA_RAW/wikidata.gz | parallel --progress --pipe --block 100m perl parse_wikidata.pl >> $GD_DATA_PARSED/wikidata/entities.tsv
