package be.koder.library.domain.event

import be.koder.library.vocabulary.domain.AggregateId
import be.koder.library.vocabulary.event.EventId
import be.koder.library.vocabulary.time.Timestamp

interface Event {
    fun getId(): EventId
    fun getOccurredOn(): Timestamp
    fun getTags(): Set<AggregateId>
}