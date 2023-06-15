package be.koder.library.rdbms.event.stored

import be.koder.library.domain.author.event.AuthorModified

data class StoredAuthorModified(
    override val id: String,
    override val occurredOn: String,
    val firstName: String,
    val lastName: String,
    val emailAddress: String
) : StoredEvent {
    constructor(event: AuthorModified) : this(
        event.id.toString(),
        event.occurredOn.toString(),
        event.firstName.toString(),
        event.lastName.toString(),
        event.emailAddress.toString()
    )
}