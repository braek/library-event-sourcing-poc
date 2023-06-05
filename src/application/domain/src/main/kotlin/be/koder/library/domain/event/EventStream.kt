package be.koder.library.domain.event

data class EventStream(private val events: List<Event>) : Iterable<Event> {
    fun isEmpty(): Boolean {
        return events.isEmpty()
    }

    companion object {
        @JvmStatic
        fun empty(): EventStream {
            return EventStream(emptyList())
        }
    }

    override fun iterator(): Iterator<Event> {
        return events.iterator();
    }
}