/*
 * This file is generated by jOOQ.
 */
package be.koder.library.rdbms.tables


import be.koder.library.rdbms.Sandbox
import be.koder.library.rdbms.indexes.TAGS_INDEX
import be.koder.library.rdbms.keys.EVENT_STORE_PKEY
import be.koder.library.rdbms.keys.EVENT_STORE_SEQUENCE_ID_KEY
import be.koder.library.rdbms.tables.records.EventStoreRecord

import java.time.OffsetDateTime
import java.util.UUID

import kotlin.collections.List

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Identity
import org.jooq.Index
import org.jooq.JSONB
import org.jooq.Name
import org.jooq.Record
import org.jooq.Row7
import org.jooq.Schema
import org.jooq.Table
import org.jooq.TableField
import org.jooq.TableOptions
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal
import org.jooq.impl.SQLDataType
import org.jooq.impl.TableImpl


/**
 * This class is generated by jOOQ.
 */
@Suppress("UNCHECKED_CAST")
open class EventStore(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, EventStoreRecord>?,
    aliased: Table<EventStoreRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<EventStoreRecord>(
    alias,
    Sandbox.SANDBOX,
    child,
    path,
    aliased,
    parameters,
    DSL.comment(""),
    TableOptions.table()
) {
    companion object {

        /**
         * The reference instance of <code>sandbox.event_store</code>
         */
        val EVENT_STORE = EventStore()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<EventStoreRecord> = EventStoreRecord::class.java

    /**
     * The column <code>sandbox.event_store.id</code>.
     */
    val ID: TableField<EventStoreRecord, UUID?> = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "")

    /**
     * The column <code>sandbox.event_store.sequence_id</code>.
     */
    val SEQUENCE_ID: TableField<EventStoreRecord, Long?> = createField(DSL.name("sequence_id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "")

    /**
     * The column <code>sandbox.event_store.persisted_on</code>.
     */
    val PERSISTED_ON: TableField<EventStoreRecord, OffsetDateTime?> = createField(DSL.name("persisted_on"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.TIMESTAMPWITHTIMEZONE)), this, "")

    /**
     * The column <code>sandbox.event_store.occurred_on</code>.
     */
    val OCCURRED_ON: TableField<EventStoreRecord, OffsetDateTime?> = createField(DSL.name("occurred_on"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false), this, "")

    /**
     * The column <code>sandbox.event_store.type</code>.
     */
    val TYPE: TableField<EventStoreRecord, String?> = createField(DSL.name("type"), SQLDataType.VARCHAR.nullable(false), this, "")

    /**
     * The column <code>sandbox.event_store.payload</code>.
     */
    val PAYLOAD: TableField<EventStoreRecord, JSONB?> = createField(DSL.name("payload"), SQLDataType.JSONB.nullable(false), this, "")

    /**
     * The column <code>sandbox.event_store.tags</code>.
     */
    val TAGS: TableField<EventStoreRecord, Array<String?>?> = createField(DSL.name("tags"), SQLDataType.VARCHAR.getArrayDataType(), this, "")

    private constructor(alias: Name, aliased: Table<EventStoreRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<EventStoreRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>sandbox.event_store</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>sandbox.event_store</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>sandbox.event_store</code> table reference
     */
    constructor(): this(DSL.name("event_store"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, EventStoreRecord>): this(Internal.createPathAlias(child, key), child, key, EVENT_STORE, null)
    override fun getSchema(): Schema = Sandbox.SANDBOX
    override fun getIndexes(): List<Index> = listOf(TAGS_INDEX)
    override fun getIdentity(): Identity<EventStoreRecord, Long?> = super.getIdentity() as Identity<EventStoreRecord, Long?>
    override fun getPrimaryKey(): UniqueKey<EventStoreRecord> = EVENT_STORE_PKEY
    override fun getKeys(): List<UniqueKey<EventStoreRecord>> = listOf(EVENT_STORE_PKEY, EVENT_STORE_SEQUENCE_ID_KEY)
    override fun `as`(alias: String): EventStore = EventStore(DSL.name(alias), this)
    override fun `as`(alias: Name): EventStore = EventStore(alias, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): EventStore = EventStore(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): EventStore = EventStore(name, null)

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row7<UUID?, Long?, OffsetDateTime?, OffsetDateTime?, String?, JSONB?, Array<String?>?> = super.fieldsRow() as Row7<UUID?, Long?, OffsetDateTime?, OffsetDateTime?, String?, JSONB?, Array<String?>?>
}
