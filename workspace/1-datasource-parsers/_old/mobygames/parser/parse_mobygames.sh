#!/bin/bash
jruby -J-Xms2G -J-Xmx4G parse_mobygames.rb --input $GD_DATA_RAW/mobygames.zip --output $GD_DATA_PARSED/mobygames/games.csv --threads 6
