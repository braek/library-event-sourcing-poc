package be.koder.library.rdbms.event.stored

import be.koder.library.domain.author.event.AuthorCreated

data class StoredAuthorCreated(
    override val id: String,
    override val occurredOn: String,
    val firstName: String,
    val lastName: String,
    val emailAddress: String
) : StoredEvent {
    constructor(event: AuthorCreated) : this(
        event.id.toString(),
        event.occurredOn.toString(),
        event.firstName.toString(),
        event.lastName.toString(),
        event.emailAddress.toString()
    )
}