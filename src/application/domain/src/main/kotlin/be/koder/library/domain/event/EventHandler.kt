package be.koder.library.domain.event

interface EventHandler {

    fun handle(event: Event)
}