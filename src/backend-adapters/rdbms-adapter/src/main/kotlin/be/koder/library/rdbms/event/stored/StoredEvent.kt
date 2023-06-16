package be.koder.library.rdbms.event.stored

import be.koder.library.domain.event.Event

interface StoredEvent {

    val eventId: String

    val occurredOn: String

    fun toEvent(): Event
}