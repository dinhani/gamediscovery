-- =============================================================================
-- SCHEMA
-- =============================================================================
DROP TABLE IF EXISTS mobygames_games;

CREATE TABLE mobygames_games(
    id VARCHAR(255),
	attributes JSONB
);

-- =============================================================================
-- DATA
-- =============================================================================
COPY mobygames_games FROM :'csv' DELIMITER E'\t' QUOTE '"' CSV HEADER;

-- =============================================================================
-- INDEX
-- =============================================================================
CREATE INDEX ON mobygames_games(id);