package be.koder.library.rdbms.event.mapping

import be.koder.library.domain.event.Event

sealed interface EventJson<T : Event> {

    val eventId: String

    val eventType: String

    val occurredOn: String

    fun toEvent(): T
}