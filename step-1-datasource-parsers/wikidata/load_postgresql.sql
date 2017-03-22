-- =============================================================================
-- SCHEMA
-- =============================================================================
DROP TABLE IF EXISTS wikidata_entities;

CREATE TABLE wikidata_entities(
    id VARCHAR(255),
    id_wikipedia VARCHAR(255),
    id_wikidata VARCHAR(255),
    link VARCHAR(255),
    link_pt VARCHAR(255),
    name VARCHAR(255),
    attributes JSONB
);

-- =============================================================================
-- DATA
-- =============================================================================
COPY wikidata_entities FROM :'csv' DELIMITER E'\t' QUOTE '"' CSV HEADER;

-- =============================================================================
-- INDEX
-- =============================================================================
CREATE INDEX ON wikidata_entities(id);
CREATE INDEX ON wikidata_entities(id_wikipedia);
CREATE INDEX ON wikidata_entities(id_wikidata);
CREATE INDEX ON wikidata_entities USING gin (attributes jsonb_path_ops);