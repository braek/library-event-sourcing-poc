package be.koder.library.domain.event

import be.koder.library.vocabulary.event.EventId

class EventStreamChangedException : RuntimeException {

    constructor(lastEventId: EventId) : super(String.format("Cannot store events: event stream changed after event with ID %s", lastEventId))
}