package be.koder.library.rdbms.event.mapper

data class AuthorCreatedPayload(
    val firstName: String,
    val lastName: String,
    val emailAddress: String
) : EventPayload