psql --set=csv_categories=$GD_DATA_PARSED/dbpedia/article_categories.csv --set=csv_category_categories=$GD_DATA_PARSED/dbpedia/category_categories.csv --set=csv_attributes=$GD_DATA_PARSED/dbpedia/article_attributes.csv --file=load_postgresql.sql