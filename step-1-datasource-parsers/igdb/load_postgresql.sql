-- =============================================================================
-- SCHEMA
-- =============================================================================
-- schema
DROP TABLE IF EXISTS igdb_characters;
DROP TABLE IF EXISTS igdb_games;
DROP TABLE IF EXISTS igdb_games_relationships;


CREATE TABLE igdb_characters(
    id VARCHAR(255),
    id_igdb VARCHAR(255),
    name VARCHAR(255),
    attributes JSONB
);

CREATE TABLE igdb_games(
    id VARCHAR(255),
    id_igdb VARCHAR(255),
    name VARCHAR(255),
    attributes JSONB
);
CREATE TABLE igdb_games_relationships(
    id VARCHAR(255),
    id_igdb VARCHAR(255),
    name VARCHAR(255),
    type VARCHAR(255),
    attributes JSONB
);

-- =============================================================================
-- DATA
-- =============================================================================
COPY igdb_characters FROM :'csv_characters' DELIMITER E'\t' QUOTE '"' CSV HEADER;

COPY igdb_games FROM :'csv_games' DELIMITER E'\t' QUOTE '"' CSV HEADER;
COPY igdb_games_relationships FROM :'csv_relationships' DELIMITER E'\t' QUOTE '"' CSV HEADER;
DELETE FROM igdb_games WHERE id = '';
DELETE FROM igdb_games_relationships WHERE id = '';

-- extract games ids from relationships
with igr as(
    select id, name, type, jsonb_array_elements(attributes->'games')::text as game_id
    from igdb_games_relationships
)
-- update based on subquery
update igdb_games set attributes = ig_joined_object.value
from (
    -- grouping generating json object
    select id_igdb, json_object_agg(type, value) as value
    from (
        -- join with relatioships grouping generating array
        select ig.id_igdb as id_igdb, igr.type, array_agg(igr.id) as value
        from igdb_games ig
        join igr on ig.id_igdb = igr.game_id
        group by ig.id_igdb, igr.type
    ) ig_joined_array
    group by id_igdb
) ig_joined_object
where igdb_games.id_igdb = ig_joined_object.id_igdb;

-- =============================================================================
-- INDEX
-- =============================================================================
CREATE INDEX ON igdb_games(id);
CREATE UNIQUE INDEX ON igdb_games(id_igdb);