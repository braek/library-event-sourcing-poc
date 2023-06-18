package be.koder.library.rdbms.event.json

import be.koder.library.domain.event.Event

interface EventJson<T : Event> {

    val eventId: String

    val occurredOn: String

    fun toEvent(): T
}