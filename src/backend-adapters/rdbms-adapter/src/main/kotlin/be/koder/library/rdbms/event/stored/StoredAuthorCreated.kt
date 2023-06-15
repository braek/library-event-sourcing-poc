package be.koder.library.rdbms.event.stored

import be.koder.library.domain.author.event.AuthorCreated
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.EmailAddress
import be.koder.library.vocabulary.author.FirstName
import be.koder.library.vocabulary.author.LastName
import be.koder.library.vocabulary.event.EventId
import be.koder.library.vocabulary.time.Timestamp

data class StoredAuthorCreated(
    override val id: String,
    override val occurredOn: String,
    val authorId: String,
    val firstName: String,
    val lastName: String,
    val emailAddress: String
) : StoredEvent {

    constructor(event: AuthorCreated) : this(
        event.id.toString(),
        event.occurredOn.toString(),
        event.authorId.toString(),
        event.firstName.toString(),
        event.lastName.toString(),
        event.emailAddress.toString()
    )

    override fun toEvent(): AuthorCreated {
        return AuthorCreated(
            EventId.fromString(id),
            Timestamp.fromString(occurredOn),
            AuthorId.fromString(authorId),
            FirstName(firstName),
            LastName(lastName),
            EmailAddress(emailAddress)
        )
    }
}