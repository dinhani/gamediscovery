#!/bin/bash
jruby -J-Xms2G -J-Xmx4G parse_cooperative.rb --input $GD_DATA_RAW/wikipedia_cooperative.html --output $GD_DATA_PARSED/wikipedia/cooperative.csv
