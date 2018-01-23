# step-1-datasource-parsers

Each folder contains all the scripts necessary to download, parse and load data into PostgreSQL for a datasource.

Some sources like DBPedia and Wikidata does not have downloaders. Their data should be downloaded directly from their websites.

Usually the steps for each source are:

1. Run downloader
2. Run parser
3. Load data into PostgreSQL
