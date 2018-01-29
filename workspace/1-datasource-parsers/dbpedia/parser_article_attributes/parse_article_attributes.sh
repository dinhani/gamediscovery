#!/bin/bash
jruby -J-Xms2G -J-Xmx4G parse_article_attributes.rb --input $GD_DATA_RAW/dbpedia_article_attributes.ttl --output $GD_DATA_PARSED/dbpedia/article_attributes.csv
