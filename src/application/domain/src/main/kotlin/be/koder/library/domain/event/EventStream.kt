package be.koder.library.domain.event

data class EventStream(private val events: List<Event>) : Iterable<Event> {

    fun isEmpty(): Boolean {
        return events.isEmpty()
    }

    override fun iterator(): Iterator<Event> {
        return events.iterator();
    }

    companion object {
        @JvmStatic
        fun empty(): EventStream {
            return EventStream(emptyList())
        }
    }
}