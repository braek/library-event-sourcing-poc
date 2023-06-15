package be.koder.library.domain.event

import be.koder.library.vocabulary.domain.AggregateId

data class EventStreamQuery(val tags: Set<AggregateId>)