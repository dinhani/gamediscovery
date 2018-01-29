#!/bin/bash
jruby -J-Xms2G -J-Xmx4G parse_scores.rb --input $GD_DATA_RAW/gamespot_scores.zip --output $GD_DATA_PARSED/gamespot/games.csv
