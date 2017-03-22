-- =============================================================================
-- SCHEMA
-- =============================================================================
DROP TABLE IF EXISTS wikipedia_articles_en;
DROP TABLE IF EXISTS wikipedia_articles_pt;
DROP TABLE IF EXISTS wikipedia_cooperative;
DROP TABLE IF EXISTS wikipedia_goty;

CREATE TABLE wikipedia_articles_en(
    id VARCHAR(255),
    id_wikipedia VARCHAR(255),
    word_count JSONB,
    summary TEXT,
    attributes JSONB
);
CREATE TABLE wikipedia_articles_pt(
    id VARCHAR(255),
    id_wikipedia VARCHAR(255),
    word_count JSONB,
    summary TEXT,
    attributes JSONB
);

CREATE TABLE wikipedia_cooperative(
    id VARCHAR(255),
    id_wikipedia VARCHAR(255),
    attributes JSONB
);

CREATE TABLE wikipedia_goty(
    id VARCHAR(255),
    id_wikipedia VARCHAR(255)
);

-- =============================================================================
-- DATA
-- =============================================================================
COPY wikipedia_articles_en FROM :'csv_articles_en' DELIMITER E'\t' QUOTE '"' CSV HEADER;
COPY wikipedia_articles_pt FROM :'csv_articles_pt' DELIMITER E'\t' QUOTE '"' CSV HEADER;

COPY wikipedia_cooperative FROM :'csv_cooperative' DELIMITER E'\t' QUOTE '"' CSV HEADER;

COPY wikipedia_goty FROM :'csv_goty' DELIMITER E'\t' QUOTE '"' CSV HEADER;

-- =============================================================================
-- INDEX
-- =============================================================================
CREATE INDEX ON wikipedia_articles_en(id);
CREATE INDEX ON wikipedia_articles_en(id_wikipedia);

CREATE INDEX ON wikipedia_articles_pt(id);
CREATE INDEX ON wikipedia_articles_pt(id_wikipedia);

CREATE INDEX ON wikipedia_cooperative(id);
CREATE INDEX ON wikipedia_cooperative(id_wikipedia);

CREATE INDEX ON wikipedia_goty(id);
CREATE INDEX ON wikipedia_goty(id_wikipedia);