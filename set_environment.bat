@echo off

rem ============================================================================
rem DATA PARSING ENV VARS
rem These are the ones you need to change
rem ============================================================================

rem Directory where are located data downloaded from sites like Wikidata, DBPedia, etc.
rem It will also be used to store downloaded pages from crawled sites
SET GD_DATA_RAW=F:/gamediscovery/data/raw

rem Directory where parsed CSV files will be placed after the raw data is processed
rem This CSV files are used as input data to the PostgreSQL database
SET GD_DATA_PARSED=F:/gamediscovery/data/parsed

rem Directory where Neo4J is installed and will be used as database to gamediscovery
rem It is generated from the extracted PostgreSQL database data
SET GD_DATA_NEO4J=F:/gamediscovery/neo4j

rem Database connections
SET GD_NEO4J_CONN_REST=http://localhost:7474/db/data/
SET GD_NEO4J_CONN_BOLT=bolt://localhost:7687/
SET GD_NEO4J_CONN_USER=neo4j
SET GD_NEO4J_CONN_PASSWORD=123

SET GD_POSTGRESQL_CONN=jdbc:postgresql://localhost/gamediscovery
SET GD_POSTGRESQL_CONN_DATABASE=gamediscovery
SET GD_POSTGRESQL_CONN_USER=postgres
SET GD_POSTGRESQL_CONN_PASSWORD=123

SET PGDATABASE=%GD_POSTGRESQL_CONN_DATABASE%
SET PGUSER=%GD_POSTGRESQL_CONN_USER%
SET PGPASSWORD=%GD_POSTGRESQL_CONN_PASSWORD%
SET PGCLIENTENCODING=UTF-8

rem ============================================================================
rem SITE ENV VARS
rem You don't need to change these
rem ============================================================================

rem Indicates if running in development or production mode
SET GD_ENVIRONMENT=development
