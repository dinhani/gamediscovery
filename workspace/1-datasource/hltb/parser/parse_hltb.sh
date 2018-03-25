#!/bin/bash
jruby -J-Xms2G -J-Xmx4G parse_hltb.rb --input $GD_DATA_RAW/hltb.zip --output $GD_DATA_PARSED/hltb/games.csv --threads 6
