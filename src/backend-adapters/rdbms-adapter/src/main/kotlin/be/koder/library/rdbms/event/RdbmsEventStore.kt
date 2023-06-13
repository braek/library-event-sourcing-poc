package be.koder.library.rdbms.event

import be.koder.library.domain.event.Event
import be.koder.library.domain.event.EventStore
import be.koder.library.domain.event.EventStream
import be.koder.library.rdbms.mapper.JsonbMapper
import be.koder.library.rdbms.tables.records.EventStoreRecord
import be.koder.library.rdbms.tables.references.EVENT_STORE
import be.koder.library.vocabulary.domain.AggregateId
import org.jooq.DSLContext
import org.springframework.transaction.annotation.Transactional

@Transactional
open class RdbmsEventStore(private val dsl: DSLContext) : EventStore {

    override fun append(eventStream: EventStream) {
        val records = mutableListOf<EventStoreRecord>()
        eventStream.forEach {
            records.add(mapRecord(it))
        }
        dsl.batchInsert(records).execute()
    }

    private fun mapRecord(event: Event): EventStoreRecord {
        val record = dsl.newRecord(EVENT_STORE)
        record.id = event.id().getValue()
        record.occurredOn = event.occurredOn().toOffsetDateTime()
        record.type = event.javaClass.simpleName
        record.tags = mapTags(event.tags())
        record.payload = JsonbMapper.write(event)
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