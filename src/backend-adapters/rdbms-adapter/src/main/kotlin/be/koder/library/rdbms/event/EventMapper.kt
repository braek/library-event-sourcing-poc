package be.koder.library.rdbms.event

import be.koder.library.domain.author.event.AuthorCreated
import be.koder.library.domain.event.Event
import be.koder.library.rdbms.event.stored.StoredAuthorCreated
import be.koder.library.rdbms.event.stored.StoredEventMapper
import be.koder.library.rdbms.tables.records.EventStoreRecord
import be.koder.library.vocabulary.domain.AggregateId
import be.koder.library.vocabulary.event.EventId
import be.koder.library.vocabulary.time.Timestamp

object EventMapper {

    fun map(record: EventStoreRecord): Event {
        val id = EventId.fromUuid(record.id!!)
        val occurredOn = Timestamp.fromOffsetDateTime(record.occurredOn!!)
        val tags = mapTags(record.tags!!)
        if (AuthorCreated::class.java.simpleName.equals(record.type)) {
            val payload = StoredEventMapper.read(record.payload!!, StoredAuthorCreated::class.java)
        }
        throw IllegalArgumentException("Cannot map EventRecord to Event")
    }

    fun mapTags(tags: Array<String?>): Set<AggregateId> {
        val converted = mutableSetOf<AggregateId>()
        return converted.toSet()
    }
}