package be.koder.library.rdbms.event.mapper

import be.koder.library.domain.event.Event
import be.koder.library.rdbms.tables.records.EventRecord
import be.koder.library.rdbms.tables.references.EVENT
import be.koder.library.vocabulary.domain.AggregateId
import org.jooq.DSLContext

object EventRecordMapper {

    fun map(event: Event, dsl: DSLContext): EventRecord {
        val record = dsl.newRecord(EVENT)
        record.id = event.id().getValue()
        record.type = event.javaClass.simpleName
        record.tags = mapTags(event.tags())
        record.payload = PayloadMapper.convertPayloadToJson(event)
        return record
    }

    private fun mapTags(tags: Set<AggregateId>): Array<String?> {
        val result = mutableSetOf<String>()
        tags.forEach {
            result.add(it.javaClass.simpleName + "#" + it.getValue().toString())
        }
        return result.toTypedArray()
    }
}