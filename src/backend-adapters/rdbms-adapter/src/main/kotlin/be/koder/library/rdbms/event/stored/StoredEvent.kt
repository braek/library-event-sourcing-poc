package be.koder.library.rdbms.event.stored

import be.koder.library.domain.event.Event

interface StoredEvent<T : Event> {

    val eventId: String

    val occurredOn: String

    fun toEvent(): T
}