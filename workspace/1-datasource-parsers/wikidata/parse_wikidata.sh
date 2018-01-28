echo -e "source\ttype\ttarget" > $GD_DATA_PARSED/wikidata/entities.tsv
gzip -d -c wikidata.gz $GD_DATA_RAW/wikidata.gz | head -n 100000 | perl parse_wikidata.pl
