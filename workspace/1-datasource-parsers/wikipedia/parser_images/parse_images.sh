#!/bin/bash
jruby -J-Xms2G -J-Xmx4G parse_images.rb --input $GD_DATA_RAW/wikipedia_images.zip --output $GD_DATA_PARSED/wikipedia/images_converted.zip
