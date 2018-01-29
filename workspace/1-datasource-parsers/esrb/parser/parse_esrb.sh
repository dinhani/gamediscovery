#!/bin/bash
jruby -J-Xms2G -J-Xmx4G parse_esrb.rb --input $GD_DATA_RAW/esrb.zip --output $GD_DATA_PARSED/esrb/games.csv --threads 2
