CREATE TABLE event_store
(
    id           UUID                     NOT NULL PRIMARY KEY,
    sequence_id  BIGSERIAL                NOT NULL UNIQUE,
    persisted_on TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    occurred_on  TIMESTAMP WITH TIME ZONE NOT NULL,
    type         VARCHAR                  NOT NULL,
    payload      JSONB                    NOT NULL,
    tags         VARCHAR[] NOT NULL
);
CREATE INDEX tags_index ON event_store USING GIN(tags);