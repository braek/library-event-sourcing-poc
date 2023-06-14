/*
 * This file is generated by jOOQ.
 */
package be.koder.library.rdbms.tables


import be.koder.library.rdbms.Sandbox
import be.koder.library.rdbms.indexes.TAGS_INDEX
import be.koder.library.rdbms.keys.EVENT_PKEY
import be.koder.library.rdbms.keys.EVENT_SEQUENCE_ID_KEY
import be.koder.library.rdbms.tables.records.EventRecord

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
open class Event(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, EventRecord>?,
    aliased: Table<EventRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<EventRecord>(
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
         * The reference instance of <code>sandbox.event</code>
         */
        val EVENT = Event()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<EventRecord> = EventRecord::class.java

    /**
     * The column <code>sandbox.event.id</code>.
     */
    val ID: TableField<EventRecord, UUID?> = createField(DSL.name("id"), SQLDataType.UUID.nullable(false), this, "")

    /**
     * The column <code>sandbox.event.sequence_id</code>.
     */
    val SEQUENCE_ID: TableField<EventRecord, Long?> = createField(DSL.name("sequence_id"), SQLDataType.BIGINT.nullable(false).identity(true), this, "")

    /**
     * The column <code>sandbox.event.persisted_on</code>.
     */
    val PERSISTED_ON: TableField<EventRecord, OffsetDateTime?> = createField(DSL.name("persisted_on"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false).defaultValue(DSL.field("CURRENT_TIMESTAMP", SQLDataType.TIMESTAMPWITHTIMEZONE)), this, "")

    /**
     * The column <code>sandbox.event.occurred_on</code>.
     */
    val OCCURRED_ON: TableField<EventRecord, OffsetDateTime?> = createField(DSL.name("occurred_on"), SQLDataType.TIMESTAMPWITHTIMEZONE(6).nullable(false), this, "")

    /**
     * The column <code>sandbox.event.type</code>.
     */
    val TYPE: TableField<EventRecord, String?> = createField(DSL.name("type"), SQLDataType.VARCHAR.nullable(false), this, "")

    /**
     * The column <code>sandbox.event.payload</code>.
     */
    val PAYLOAD: TableField<EventRecord, JSONB?> = createField(DSL.name("payload"), SQLDataType.JSONB.nullable(false), this, "")

    /**
     * The column <code>sandbox.event.tags</code>.
     */
    val TAGS: TableField<EventRecord, Array<String?>?> = createField(DSL.name("tags"), SQLDataType.VARCHAR.getArrayDataType(), this, "")

    private constructor(alias: Name, aliased: Table<EventRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<EventRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>sandbox.event</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>sandbox.event</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>sandbox.event</code> table reference
     */
    constructor(): this(DSL.name("event"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, EventRecord>): this(Internal.createPathAlias(child, key), child, key, EVENT, null)
    override fun getSchema(): Schema = Sandbox.SANDBOX
    override fun getIndexes(): List<Index> = listOf(TAGS_INDEX)
    override fun getIdentity(): Identity<EventRecord, Long?> = super.getIdentity() as Identity<EventRecord, Long?>
    override fun getPrimaryKey(): UniqueKey<EventRecord> = EVENT_PKEY
    override fun getKeys(): List<UniqueKey<EventRecord>> = listOf(EVENT_PKEY, EVENT_SEQUENCE_ID_KEY)
    override fun `as`(alias: String): Event = Event(DSL.name(alias), this)
    override fun `as`(alias: Name): Event = Event(alias, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): Event = Event(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): Event = Event(name, null)

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row7<UUID?, Long?, OffsetDateTime?, OffsetDateTime?, String?, JSONB?, Array<String?>?> = super.fieldsRow() as Row7<UUID?, Long?, OffsetDateTime?, OffsetDateTime?, String?, JSONB?, Array<String?>?>
}
