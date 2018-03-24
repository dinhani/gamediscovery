-- =============================================================================
-- SCHEMA
-- =============================================================================
DROP TABLE IF EXISTS dbpedia_article_categories;
DROP TABLE IF EXISTS dbpedia_article_attributes;
DROP TABLE IF EXISTS dbpedia_category_categories;

CREATE TABLE dbpedia_article_categories(
    id VARCHAR(255),
    id_wikipedia VARCHAR(255),
    categories TEXT[]
);

CREATE TABLE dbpedia_article_attributes(
    id VARCHAR(255),
    id_wikipedia VARCHAR(255),
    attributes JSONB
);
CREATE TABLE dbpedia_category_categories(
    id VARCHAR(255),
    id_wikipedia VARCHAR(255),
    categories TEXT[]
);



-- =============================================================================
-- DATA
-- =============================================================================
COPY dbpedia_article_categories FROM :'csv_categories' DELIMITER E'\t' QUOTE '"' CSV HEADER;

COPY dbpedia_category_categories FROM :'csv_category_categories' DELIMITER E'\t' QUOTE '"' CSV HEADER;

COPY dbpedia_article_attributes FROM :'csv_attributes' DELIMITER E'\t' QUOTE '"' CSV HEADER;

-- =============================================================================
-- INDEX
-- =============================================================================
CREATE INDEX ON dbpedia_article_categories(id);
CREATE INDEX ON dbpedia_article_categories(id_wikipedia);
CREATE INDEX ON dbpedia_article_categories USING GIN (categories);

CREATE INDEX ON dbpedia_category_categories(id);
CREATE INDEX ON dbpedia_category_categories USING GIN (categories);

CREATE INDEX ON dbpedia_article_attributes(id);
CREATE INDEX ON dbpedia_article_attributes(id_wikipedia);
CREATE INDEX ON dbpedia_article_attributes USING gin (attributes jsonb_path_ops);