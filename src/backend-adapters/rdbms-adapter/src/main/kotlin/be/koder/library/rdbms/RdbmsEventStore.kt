package be.koder.library.rdbms

import be.koder.library.domain.event.EventStore
import be.koder.library.domain.event.EventStream

class RdbmsEventStore : EventStore {
    override fun append(eventStream: EventStream) {
        TODO("Not yet implemented")
    }
}