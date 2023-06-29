package be.koder.library.domain.entity

import be.koder.library.vocabulary.domain.AggregateId

interface Aggregate {
    fun getId(): AggregateId
}