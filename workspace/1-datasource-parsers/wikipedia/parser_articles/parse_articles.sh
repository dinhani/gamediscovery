#!/bin/bash
jruby -J-Xms2G -J-Xmx4G parse_articles.rb --input $GD_DATA_RAW/wikipedia.zip --output $GD_DATA_PARSED/wikipedia --threads 6
