package be.koder.library.rdbms.event

import be.koder.library.domain.author.event.AuthorCreated
import be.koder.library.domain.event.Event
import be.koder.library.domain.event.EventStore
import be.koder.library.domain.event.EventStream
import be.koder.library.rdbms.mapper.JsonbMapper
import be.koder.library.rdbms.tables.records.EventRecord
import be.koder.library.rdbms.tables.references.EVENT
import org.jooq.DSLContext

class RdbmsEventStore(private val dsl: DSLContext) : EventStore {

    override fun append(eventStream: EventStream) {
        val records = mutableListOf<EventRecord>()
        eventStream.forEach {
            records.add(mapEventRecord(it))
        }
        dsl.batchInsert(records)
    }

    private fun mapEventRecord(event: Event): EventRecord {
        if (event is AuthorCreated) {
            val record = dsl.newRecord(EVENT)
            record.id = event.id.getValue()
            record.occurredOn = event.occurredOn.toOffsetDateTime()
            record.payload = JsonbMapper.write(event)
            record.type = event.javaClass.simpleName
            return record
        }
        throw IllegalArgumentException("Cannot map Event to EventRecord")
    }
}