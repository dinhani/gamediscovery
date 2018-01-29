#!/bin/bash
jruby -J-Xms2G -J-Xmx4G parse_games_relationships.rb --input $GD_DATA_RAW/igdb --output $GD_DATA_PARSED/igdb/games_relationships.csv
