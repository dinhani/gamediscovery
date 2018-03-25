#!/bin/bash
psql --set=csv=$GD_DATA_PARSED/hltb/games.csv --file=load_postgresql.sql
