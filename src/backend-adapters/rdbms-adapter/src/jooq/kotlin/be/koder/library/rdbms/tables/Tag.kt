/*
 * This file is generated by jOOQ.
 */
package be.koder.library.rdbms.tables


import be.koder.library.rdbms.Sandbox
import be.koder.library.rdbms.keys.PK_TAG
import be.koder.library.rdbms.keys.TAG__FK_TAG_EVENT
import be.koder.library.rdbms.tables.records.TagRecord

import java.util.UUID

import kotlin.collections.List

import org.jooq.Field
import org.jooq.ForeignKey
import org.jooq.Name
import org.jooq.Record
import org.jooq.Row3
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
open class Tag(
    alias: Name,
    child: Table<out Record>?,
    path: ForeignKey<out Record, TagRecord>?,
    aliased: Table<TagRecord>?,
    parameters: Array<Field<*>?>?
): TableImpl<TagRecord>(
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
         * The reference instance of <code>sandbox.tag</code>
         */
        val TAG = Tag()
    }

    /**
     * The class holding records for this type
     */
    override fun getRecordType(): Class<TagRecord> = TagRecord::class.java

    /**
     * The column <code>sandbox.tag.event_id</code>.
     */
    val EVENT_ID: TableField<TagRecord, UUID?> = createField(DSL.name("event_id"), SQLDataType.UUID.nullable(false), this, "")

    /**
     * The column <code>sandbox.tag.type</code>.
     */
    val TYPE: TableField<TagRecord, String?> = createField(DSL.name("type"), SQLDataType.VARCHAR.nullable(false), this, "")

    /**
     * The column <code>sandbox.tag.value</code>.
     */
    val VALUE: TableField<TagRecord, String?> = createField(DSL.name("value"), SQLDataType.VARCHAR.nullable(false), this, "")

    private constructor(alias: Name, aliased: Table<TagRecord>?): this(alias, null, null, aliased, null)
    private constructor(alias: Name, aliased: Table<TagRecord>?, parameters: Array<Field<*>?>?): this(alias, null, null, aliased, parameters)

    /**
     * Create an aliased <code>sandbox.tag</code> table reference
     */
    constructor(alias: String): this(DSL.name(alias))

    /**
     * Create an aliased <code>sandbox.tag</code> table reference
     */
    constructor(alias: Name): this(alias, null)

    /**
     * Create a <code>sandbox.tag</code> table reference
     */
    constructor(): this(DSL.name("tag"), null)

    constructor(child: Table<out Record>, key: ForeignKey<out Record, TagRecord>): this(Internal.createPathAlias(child, key), child, key, TAG, null)
    override fun getSchema(): Schema = Sandbox.SANDBOX
    override fun getPrimaryKey(): UniqueKey<TagRecord> = PK_TAG
    override fun getKeys(): List<UniqueKey<TagRecord>> = listOf(PK_TAG)
    override fun getReferences(): List<ForeignKey<TagRecord, *>> = listOf(TAG__FK_TAG_EVENT)

    private lateinit var _event: Event
    fun event(): Event {
        if (!this::_event.isInitialized)
            _event = Event(this, TAG__FK_TAG_EVENT)

        return _event;
    }
    override fun `as`(alias: String): Tag = Tag(DSL.name(alias), this)
    override fun `as`(alias: Name): Tag = Tag(alias, this)

    /**
     * Rename this table
     */
    override fun rename(name: String): Tag = Tag(DSL.name(name), null)

    /**
     * Rename this table
     */
    override fun rename(name: Name): Tag = Tag(name, null)

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------
    override fun fieldsRow(): Row3<UUID?, String?, String?> = super.fieldsRow() as Row3<UUID?, String?, String?>
}
