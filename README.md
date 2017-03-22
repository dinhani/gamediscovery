# Game Discovery

Game Discovery is an advanced search engine for games allowing to query by any information available.

This repository contains all the code necessary to extract data from the external datasources, generate the game graph database and also run the website.

## Development Environment

These tools should be available in system the `PATH`.

### Dependencies

#### Required

* Java 8
  * mvn
* JRuby
  * bundle
  * jbundle
* PostgreSQL
  * psql
* Neo4J
  * neo4j-import
  * neo4j-shell

#### Optional

  * Python
  * R

### Running

All the projects use environment variables as primary configuration for connections and directories.

Before running any code, configure the `set_environment.bat` with the appropriate paths and database connection settings.

After that, in the root directory:

```
  set_environment.bat
  bundle install
  jbundle install
```

## Project Structure

### step-1-datasource-parsers

Contains JRuby scripts that download and parse data from external sources like Wikidata, Wikipedia, MobyGames, etc.

Also contains batch files that loads parsed data into the PostgreSQL database.

Usually the steps for any data source are:

1. Download data (using script or directly from the a URL)
2. Parsed data to CSV
3. Load parsed data into PostgreSQL

### step-2-graph-generator

Contains a Java project that extracts relevant game information from the PostgreSQL parsed data. It generates CSV files with this data.

It also contains JRuby scripts that generates a Neo4J database from these generated CSV files.

### step-3-site

Contains a Java project that is the website. The core Java models and logic for search and recommendations is here.

Also contains JRuby scripts that helps the website to run like Lucene index generator and sitemap generator.

### step-4-reports

Not important. Contains data analysis scripts in multiple languages just to explore data visualization and discovery.
