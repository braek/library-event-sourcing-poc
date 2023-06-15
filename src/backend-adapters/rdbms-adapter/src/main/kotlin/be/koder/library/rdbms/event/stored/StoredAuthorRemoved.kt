package be.koder.library.rdbms.event.stored

import be.koder.library.domain.author.event.AuthorRemoved

data class StoredAuthorRemoved(
    override val id: String,
    override val occurredOn: String
) : StoredEvent {
    constructor(event: AuthorRemoved) : this(
        event.id.toString(),
        event.occurredOn.toString()
    )
}