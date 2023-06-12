package be.koder.library.rdbms.event

import be.koder.library.domain.author.event.AuthorCreated
import be.koder.library.domain.event.Event
import be.koder.library.domain.event.EventStore
import be.koder.library.domain.event.EventStream
import be.koder.library.rdbms.mapper.JsonbMapper
import be.koder.library.rdbms.tables.records.EventRecord
import be.koder.library.rdbms.tables.records.TagRecord
import be.koder.library.rdbms.tables.references.EVENT
import be.koder.library.rdbms.tables.references.TAG
import org.jooq.DSLContext

class RdbmsEventStore(private val dsl: DSLContext) : EventStore {

    override fun append(eventStream: EventStream) {
        val eventRecords = mutableListOf<EventRecord>()
        val tagRecords = mutableListOf<TagRecord>()
        eventStream.forEach {
            eventRecords.add(mapEventRecord(it))
            tagRecords.addAll(mapTagRecords(it))
        }
        dsl.batchInsert(eventRecords)
        dsl.batchInsert(tagRecords)
    }

    private fun mapTagRecords(event: Event): List<TagRecord> {
        val records = mutableListOf<TagRecord>()
        event.tags().forEach {
            val record = dsl.newRecord(TAG)
            record.eventId = event.id().getValue()
            record.type = it.javaClass.simpleName
            record.value = it.getValue().toString()
            records.add(record)
        }
        return records.toList()
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