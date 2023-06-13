package be.koder.library.rdbms.event

import be.koder.library.domain.event.EventStore
import be.koder.library.domain.event.EventStream
import be.koder.library.rdbms.event.mapper.EventRecordMapper
import be.koder.library.rdbms.tables.records.EventRecord
import org.jooq.DSLContext
import org.springframework.transaction.annotation.Transactional

@Transactional
open class RdbmsEventStore(private val dsl: DSLContext) : EventStore {

    override fun append(eventStream: EventStream) {
        val records = mutableListOf<EventRecord>()
        eventStream.forEach {
            records.add(EventRecordMapper.map(it, dsl))
        }
        dsl.batchInsert(records).execute()
    }
}