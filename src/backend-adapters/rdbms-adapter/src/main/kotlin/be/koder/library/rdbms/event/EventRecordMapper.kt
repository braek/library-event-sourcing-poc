package be.koder.library.rdbms.event

import be.koder.library.domain.event.Event
import be.koder.library.rdbms.event.stored.StoredEventMapper
import be.koder.library.rdbms.tables.records.EventStoreRecord
import be.koder.library.rdbms.tables.references.EVENT_STORE
import org.jooq.DSLContext
import java.util.stream.Collectors

object EventRecordMapper {
    fun map(event: Event, dsl: DSLContext): EventStoreRecord {
        val record = dsl.newRecord(EVENT_STORE)
        record.id = event.id().getValue()
        record.type = event.javaClass.simpleName
        record.occurredOn = event.occurredOn().toOffsetDateTime()
        record.tags = event.tags()
            .stream()
            .map { TagMapper.map(it) }
            .collect(Collectors.toSet())
            .toTypedArray()
        record.payload = StoredEventMapper.convertPayloadToJson(event)
        return record
    }
}