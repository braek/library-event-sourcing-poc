package be.koder.library.domain.event

import be.koder.library.vocabulary.domain.AggregateId
import be.koder.library.vocabulary.event.EventId
import be.koder.library.vocabulary.time.Timestamp

interface Event {
    val id: EventId
    val occurredOn: Timestamp
    val tags: Set<AggregateId>
}