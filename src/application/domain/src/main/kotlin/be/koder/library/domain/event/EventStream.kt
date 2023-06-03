package be.koder.library.domain.event

data class EventStream(private val events: List<Event>) {
    fun isEmpty(): Boolean {
        return events.isEmpty()
    }
}