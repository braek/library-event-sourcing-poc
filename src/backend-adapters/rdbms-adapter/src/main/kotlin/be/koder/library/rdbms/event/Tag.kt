package be.koder.library.rdbms.event

import be.koder.library.vocabulary.domain.AggregateId

class Tag(aggregateId: AggregateId) {

    private val value: String

    init {
        value = aggregateId::class.java.simpleName + "#" + aggregateId.getValue().toString()
    }

    override fun toString(): String {
        return value
    }
}