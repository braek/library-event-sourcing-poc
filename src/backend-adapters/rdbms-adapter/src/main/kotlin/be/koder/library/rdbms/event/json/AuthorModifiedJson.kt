package be.koder.library.rdbms.event.json

import be.koder.library.domain.author.event.AuthorModified
import be.koder.library.rdbms.mapper.TimestampMapper
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.EmailAddress
import be.koder.library.vocabulary.author.FirstName
import be.koder.library.vocabulary.author.LastName
import be.koder.library.vocabulary.event.EventId
import be.koder.library.vocabulary.time.Timestamp

data class AuthorModifiedJson internal constructor(
    override val eventId: String,
    override val eventType: String,
    override val occurredOn: String,
    val authorId: String,
    val firstName: String,
    val lastName: String,
    val emailAddress: String
) : EventJson<AuthorModified> {

    constructor(event: AuthorModified) : this(
        event.id.toString(),
        AuthorModified::class.simpleName!!,
        TimestampMapper.map(event.occurredOn),
        event.authorId.toString(),
        event.firstName.toString(),
        event.lastName.toString(),
        event.emailAddress.toString()
    )

    override fun toEvent(): AuthorModified {
        return AuthorModified(
            EventId.fromString(eventId),
            Timestamp.fromString(occurredOn),
            AuthorId.fromString(authorId),
            FirstName(firstName),
            LastName(lastName),
            EmailAddress(emailAddress)
        )
    }
}