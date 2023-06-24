package be.koder.library.rdbms.event

import be.koder.library.domain.aggregate.EventSourcedAggregate
import be.koder.library.domain.event.EventStore
import be.koder.library.domain.event.EventStream
import be.koder.library.rdbms.event.json.EventJsonMapper
import be.koder.library.rdbms.tables.records.EventStoreRecord
import be.koder.library.rdbms.tables.references.EVENT_STORE
import be.koder.library.vocabulary.domain.AggregateId
import be.koder.library.vocabulary.event.EventId
import org.jooq.DSLContext
import org.jooq.impl.DSL.orderBy
import org.springframework.transaction.annotation.Transactional
import java.lang.RuntimeException

@Transactional
open class RdbmsEventStore(private val dsl: DSLContext) : EventStore {

    override fun appendMutations(aggregate: EventSourcedAggregate) {
        if (aggregate.noStateChanges()) {
            return
        }
        if (aggregate.getLastEventId() != getLastEventId(aggregate.getId())) {
            throw RuntimeException(String.format("Optimistic Locking Exception: new events were stored after event with ID %s", aggregate.getLastEventId()))
        }
        append(aggregate.getMutations())
    }

    override fun append(eventStream: EventStream) {
        val records = mutableListOf<EventStoreRecord>()
        eventStream.forEach {
            records.add(EventRecordMapper.map(it, dsl))
        }
        dsl.batchInsert(records).execute()
    }

    override fun query(aggregateId: AggregateId): EventStream {
        return EventStream(dsl.selectFrom(EVENT_STORE)
            .where(EVENT_STORE.TAGS.contains(arrayOf(TagMapper.map(aggregateId))))
            .orderBy(EVENT_STORE.SEQUENCE_ID.asc())
            .fetch()
            .map { EventJsonMapper.toEvent(it.payload!!, it.type!!) }
            .toList()
        )
    }

    override fun query(vararg types: String): EventStream {
        return EventStream(dsl.selectFrom(EVENT_STORE)
            .where(EVENT_STORE.TYPE.`in`(types.asList()))
            .orderBy(EVENT_STORE.SEQUENCE_ID.asc())
            .fetch()
            .map { EventJsonMapper.toEvent(it.payload!!, it.type!!) }
            .toList()
        )
    }

    override fun getLastEventId(aggregateId: AggregateId): EventId? {
        return null
    }
}