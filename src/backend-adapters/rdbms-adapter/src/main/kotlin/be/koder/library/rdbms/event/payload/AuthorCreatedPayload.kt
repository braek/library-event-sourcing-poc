package be.koder.library.rdbms.event.payload

data class AuthorCreatedPayload(
    val firstName: String,
    val lastName: String,
    val emailAddress: String
) : EventPayload