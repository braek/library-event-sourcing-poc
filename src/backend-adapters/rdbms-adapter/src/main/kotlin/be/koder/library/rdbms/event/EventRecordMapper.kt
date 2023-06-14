package be.koder.library.rdbms.event

import be.koder.library.domain.event.Event
import be.koder.library.rdbms.event.payload.EventPayloadMapper
import be.koder.library.rdbms.tables.records.EventRecord
import be.koder.library.rdbms.tables.references.EVENT
import org.jooq.DSLContext
import java.util.stream.Collectors

object EventRecordMapper {
    fun map(event: Event, dsl: DSLContext): EventRecord {
        val record = dsl.newRecord(EVENT)
        record.id = event.id().getValue()
        record.type = event.javaClass.simpleName
        record.tags = event.tags()
            .stream()
            .map { TagMapper.map(it) }
            .collect(Collectors.toSet())
            .toTypedArray()
        record.payload = EventPayloadMapper.convertPayloadToJson(event)
        return record
    }
}