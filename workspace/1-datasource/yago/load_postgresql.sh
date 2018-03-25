#!/bin/bash
psql --set=csv=$GD_DATA_RAW/yago_facts.tsv --file=load_postgresql.sql
