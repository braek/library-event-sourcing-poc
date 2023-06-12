package be.koder.library.rdbms.mapper

import be.koder.library.vocabulary.domain.AggregateId
import be.koder.library.vocabulary.event.EventId
import be.koder.library.vocabulary.time.Timestamp
import com.fasterxml.jackson.annotation.JsonIgnore

abstract class EventMixin {

    @JsonIgnore
    abstract fun id(): EventId

    @JsonIgnore
    abstract fun occurredOn(): Timestamp

    @JsonIgnore
    abstract fun tags(): Set<AggregateId>
}