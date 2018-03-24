#!/bin/bash
jruby -J-Xms2G -J-Xmx4G parse_psnprofiles.rb --input $GD_DATA_RAW/psnprofiles.zip --output $GD_DATA_PARSED/psnprofiles/games.csv --threads 6
