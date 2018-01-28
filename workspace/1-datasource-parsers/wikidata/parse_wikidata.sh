echo -e "source\ttype\ttarget" > $GD_DATA_PARSED/wikidata/entities.tsv
bzcat $GD_DATA_RAW/wikidata.bz2 | head -n 50000 | perl parse_wikidata.pl
