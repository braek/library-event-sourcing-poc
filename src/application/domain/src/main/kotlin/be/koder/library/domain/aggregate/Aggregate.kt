package be.koder.library.domain.aggregate

import be.koder.library.vocabulary.domain.AggregateId

interface Aggregate {
    fun getId(): AggregateId
}