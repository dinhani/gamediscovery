#!/bin/bash
jruby -J-Xms2G -J-Xmx4G parse_goty.rb --input $GD_DATA_RAW/wikipedia_goty.html --output $GD_DATA_PARSED/wikipedia/goty.csv
