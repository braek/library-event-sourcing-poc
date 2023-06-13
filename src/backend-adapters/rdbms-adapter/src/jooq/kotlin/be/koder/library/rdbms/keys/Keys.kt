/*
 * This file is generated by jOOQ.
 */
package be.koder.library.rdbms.keys


import be.koder.library.rdbms.tables.EventStore
import be.koder.library.rdbms.tables.FlywaySchemaHistory
import be.koder.library.rdbms.tables.records.EventStoreRecord
import be.koder.library.rdbms.tables.records.FlywaySchemaHistoryRecord

import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal



// -------------------------------------------------------------------------
// UNIQUE and PRIMARY KEY definitions
// -------------------------------------------------------------------------

val EVENT_STORE_PKEY: UniqueKey<EventStoreRecord> = Internal.createUniqueKey(EventStore.EVENT_STORE, DSL.name("event_store_pkey"), arrayOf(EventStore.EVENT_STORE.ID), true)
val EVENT_STORE_SEQUENCE_ID_KEY: UniqueKey<EventStoreRecord> = Internal.createUniqueKey(EventStore.EVENT_STORE, DSL.name("event_store_sequence_id_key"), arrayOf(EventStore.EVENT_STORE.SEQUENCE_ID), true)
val FLYWAY_SCHEMA_HISTORY_PK: UniqueKey<FlywaySchemaHistoryRecord> = Internal.createUniqueKey(FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY, DSL.name("flyway_schema_history_pk"), arrayOf(FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.INSTALLED_RANK), true)
