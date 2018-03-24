#!/bin/bash
jruby -J-Xms2G -J-Xmx4G parse_games_base.rb --input $GD_DATA_RAW/igdb_games.zip --output $GD_DATA_PARSED/igdb/games_base.csv --threads 6
