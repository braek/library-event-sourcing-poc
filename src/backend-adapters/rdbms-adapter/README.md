# Tables

```sql
CREATE TABLE event_store
(
    id           UUID                     NOT NULL PRIMARY KEY,
    sequence_id  BIGSERIAL                NOT NULL UNIQUE,
    version      INTEGER                  NOT NULL DEFAULT 1,
    persisted_on TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    occurred_on  TIMESTAMP WITH TIME ZONE NOT NULL,
    tags         VARCHAR[] NOT NULL,
    type         VARCHAR                  NOT NULL,
    payload      JSONB                    NOT NULL
);
CREATE INDEX tags_index ON event_store USING GIN(tags);
```