package be.koder.library.rdbms.event.payload

data class AuthorModifiedPayload(
    val firstName: String,
    val lastName: String,
    val emailAddress: String
) : EventPayload