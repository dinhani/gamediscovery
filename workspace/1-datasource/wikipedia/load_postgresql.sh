psql --set=csv_articles_en=$GD_DATA_PARSED/wikipedia/articles_en.csv --set=csv_articles_pt=$GD_DATA_PARSED/wikipedia/articles_pt.csv --set=csv_cooperative=$GD_DATA_PARSED/wikipedia/cooperative.csv --set=csv_goty=$GD_DATA_PARSED/wikipedia/goty.csv --file=load_postgresql.sql