package be.koder.library.rdbms.event

import be.koder.library.domain.event.EventStore
import be.koder.library.domain.event.EventStream
import be.koder.library.domain.event.EventStreamQuery
import be.koder.library.rdbms.event.stored.StoredEventMapper
import be.koder.library.rdbms.tables.records.EventStoreRecord
import be.koder.library.rdbms.tables.references.EVENT_STORE
import org.jooq.DSLContext
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Transactional
open class RdbmsEventStore(private val dsl: DSLContext) : EventStore {

    override fun append(eventStream: EventStream) {
        val records = mutableListOf<EventStoreRecord>()
        eventStream.forEach {
            records.add(EventRecordMapper.map(it, dsl))
        }
        dsl.batchInsert(records).execute()
    }

    override fun query(query: EventStreamQuery): EventStream {
        val tags = query.tags.stream()
            .map { TagMapper.map(it) }
            .collect(Collectors.toSet())
        return EventStream(dsl.selectFrom(EVENT_STORE)
            .where(EVENT_STORE.TAGS.contains(tags.toTypedArray()))
            .orderBy(EVENT_STORE.SEQUENCE_ID.asc())
            .fetch()
            .map { StoredEventMapper.toEvent(it.payload!!, it.type!!) }
            .toList()
        )
    }
}