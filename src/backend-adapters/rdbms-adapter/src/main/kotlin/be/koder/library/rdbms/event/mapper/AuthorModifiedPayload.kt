package be.koder.library.rdbms.event.mapper

data class AuthorModifiedPayload(
    val firstName: String,
    val lastName: String,
    val emailAddress: String
) : EventPayload