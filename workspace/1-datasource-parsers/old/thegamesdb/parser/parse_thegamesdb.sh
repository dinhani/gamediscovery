#!/bin/bash
jruby -J-Xms2G -J-Xmx4G parse_thegamesdb.rb --input $GD_DATA_RAW/thegamesdb.zip --output $GD_DATA_PARSED/thegamesdb/games.csv --threads 6
