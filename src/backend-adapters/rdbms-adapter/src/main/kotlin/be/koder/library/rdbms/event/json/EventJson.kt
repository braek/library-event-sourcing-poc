package be.koder.library.rdbms.event.json

import be.koder.library.domain.event.Event

sealed interface EventJson<T : Event> {

    val eventId: String

    val occurredOn: String

    val eventType: String

    fun toEvent(): T
}