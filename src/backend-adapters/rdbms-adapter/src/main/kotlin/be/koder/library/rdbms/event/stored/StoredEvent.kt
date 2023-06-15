package be.koder.library.rdbms.event.stored

interface StoredEvent {
    val id: String
    val occurredOn: String
}