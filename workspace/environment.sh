#!/bin/bash

# ============================================================================
# DATA PARSING ENV VARS
# These are the ones you need to change
# ============================================================================

# Directory where are located data downloaded from sites like Wikidata, DBPedia, etc.
# It will also be used to store downloaded pages from crawled sites
export GD_DATA_RAW=/home/vagrant/data/data/raw

# Directory where parsed CSV files will be placed after the raw data is processed
# This CSV files are used as input data to the PostgreSQL database
export GD_DATA_PARSED=/home/vagrant/data/data/parsed

# Directory where Neo4J is installed and will be used as database to gamediscovery
# It is generated from the extracted PostgreSQL database data
export GD_DATA_NEO4J=/home/vagrant/data/neo4j

# Directory where Lucene index are stored
# It is generated from the Neo4J database
export GD_DATA_LUCENE=/home/vagrant/data/lucene

# Directory where game covers are stored
export GD_DATA_IMAGES=/home/vagrant/data/images

# Database connections
export GD_NEO4J_CONN_REST=http://localhost:7474/db/data/
export GD_NEO4J_CONN_BOLT=bolt://localhost:7687/
export GD_NEO4J_CONN_USER=neo4j
export GD_NEO4J_CONN_PASSWORD=123

export GD_POSTGRESQL_CONN=jdbc:postgresql://localhost/gamediscovery
export GD_POSTGRESQL_CONN_DATABASE=gamediscovery
export GD_POSTGRESQL_CONN_USER=postgres
export GD_POSTGRESQL_CONN_PASSWORD=123

export PGDATABASE=$GD_POSTGRESQL_CONN_DATABASE
export PGUSER=$GD_POSTGRESQL_CONN_USER
export PGPASSWORD=$GD_POSTGRESQL_CONN_PASSWORD
export PGCLIENTENCODING=UTF-8

# ============================================================================
# SITE ENV VARS
# You don't need to change these
# ============================================================================

# Indicates if running in development or production mode
export GD_ENVIRONMENT=development
