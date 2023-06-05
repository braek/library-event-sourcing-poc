package be.koder.library.domain.event

import be.koder.library.vocabulary.domain.AggregateId
import be.koder.library.vocabulary.event.EventId
import be.koder.library.vocabulary.time.Timestamp

interface Event {

    fun id(): EventId

    fun occurredOn(): Timestamp

    fun tags(): Set<AggregateId>
}