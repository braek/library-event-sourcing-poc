package be.koder.library.rdbms.event

import be.koder.library.domain.event.Event
import be.koder.library.rdbms.tables.records.EventStoreRecord

object EventMapper {

    fun map(record: EventStoreRecord): Event {
        throw IllegalArgumentException("Cannot map EventRecord to Event")
    }
}