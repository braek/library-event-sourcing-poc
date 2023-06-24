package be.koder.library.rdbms.event

import be.koder.library.domain.aggregate.EventSourcedAggregate
import be.koder.library.domain.event.EventStore
import be.koder.library.domain.event.EventStream
import be.koder.library.rdbms.tables.records.EventStoreRecord
import be.koder.library.vocabulary.domain.AggregateId
import be.koder.library.vocabulary.event.EventId
import org.jooq.DSLContext
import org.springframework.transaction.annotation.Transactional

@Transactional
open class RdbmsEventStore(private val dsl: DSLContext) : EventStore {

    override fun appendMutations(aggregate: EventSourcedAggregate) {
        if (aggregate.noStateChanges()) {
            return
        }
    }

    override fun append(eventStream: EventStream) {
        val records = mutableListOf<EventStoreRecord>()
        eventStream.forEach {
            records.add(EventRecordMapper.map(it, dsl))
        }
        dsl.batchInsert(records).execute()
    }

    override fun query(aggregateId: AggregateId): EventStream {
//        val tags = query.tags.stream()
//            .map { TagMapper.map(it) }
//            .collect(Collectors.toSet())
//        return EventStream(dsl.selectFrom(EVENT_STORE)
//            .where(EVENT_STORE.TAGS.contains(tags.toTypedArray()))
//            .and(EVENT_STORE.TYPE.`in`(query.types))
//            .orderBy(EVENT_STORE.SEQUENCE_ID.asc())
//            .fetch()
//            .map { EventJsonMapper.toEvent(it.payload!!, it.type!!) }
//            .toList()
//        )
        return EventStream.empty()
    }

    override fun query(vararg types: String): EventStream {
        TODO("Not yet implemented")
    }

    override fun getLastEventId(aggregateId: AggregateId): EventId? {
        TODO("Not yet implemented")
    }
}