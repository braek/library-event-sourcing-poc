package be.koder.library.domain.repository

import be.koder.library.domain.entity.Aggregate
import be.koder.library.vocabulary.domain.AggregateId
import java.util.*

interface Repository<ID : AggregateId, AGGREGATE : Aggregate> {

    fun get(id: ID): Optional<AGGREGATE>

    fun save(aggregate: AGGREGATE)
}