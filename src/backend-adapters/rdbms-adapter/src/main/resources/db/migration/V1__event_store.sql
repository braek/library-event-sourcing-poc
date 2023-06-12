CREATE TABLE event
(
    id          UUID                     NOT NULL PRIMARY KEY,
    sequence_id BIGSERIAL                NOT NULL UNIQUE,
    occurred_on TIMESTAMP WITH TIME ZONE NOT NULL,
    type        VARCHAR                  NOT NULL,
    payload     JSONB                    NOT NULL
);
CREATE TABLE tag
(
    event_id UUID    NOT NULL,
    type     VARCHAR NOT NULL,
    value    VARCHAR NOT NULL,
    CONSTRAINT pk_tag PRIMARY KEY (event_id, type, value),
    CONSTRAINT fk_tag_event FOREIGN KEY (event_id) REFERENCES event (id)
);