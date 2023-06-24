package be.koder.library.rdbms.event

import be.koder.library.domain.event.EventStore
import be.koder.library.domain.event.EventStream
import be.koder.library.rdbms.event.json.EventJsonMapper
import be.koder.library.rdbms.tables.records.EventStoreRecord
import be.koder.library.rdbms.tables.references.EVENT_STORE
import be.koder.library.vocabulary.domain.AggregateId
import be.koder.library.vocabulary.event.EventId
import org.jooq.DSLContext
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Transactional
open class RdbmsEventStore(private val dsl: DSLContext) : EventStore {

    override fun append(aggregateId: AggregateId, mutations: EventStream, lastEventId: EventId?) {
        val records = mutableListOf<EventStoreRecord>()
        mutations.forEach {
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
            .and(EVENT_STORE.TYPE.`in`(query.types))
            .orderBy(EVENT_STORE.SEQUENCE_ID.asc())
            .fetch()
            .map { EventJsonMapper.toEvent(it.payload!!, it.type!!) }
            .toList()
        )
    }
}