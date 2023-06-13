CREATE TABLE event
(
    id          UUID                     NOT NULL PRIMARY KEY,
    sequence_id BIGSERIAL                NOT NULL UNIQUE,
    occurred_on TIMESTAMP WITH TIME ZONE NOT NULL,
    type        VARCHAR                  NOT NULL,
    payload     JSONB                    NOT NULL,
    tags        VARCHAR[] NOT NULL
);