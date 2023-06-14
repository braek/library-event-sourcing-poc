package be.koder.library.rdbms.event.payload

import be.koder.library.rdbms.event.payload.EventPayload

data class AuthorCreatedPayload(
    val firstName: String,
    val lastName: String,
    val emailAddress: String
) : EventPayload