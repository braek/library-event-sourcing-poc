package be.koder.library.test

import be.koder.library.domain.author.Author
import be.koder.library.domain.author.AuthorRepository
import be.koder.library.domain.author.EmailService
import be.koder.library.domain.author.event.AuthorCreated
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.EmailAddress
import java.lang.RuntimeException
import java.util.*
import kotlin.collections.HashSet

class InMemoryAuthorRepository(private val eventStore: MockEventStore) : AuthorRepository, EmailService {

    override fun exists(email: EmailAddress): Boolean {
        val eventStream = eventStore.query(AuthorCreated::class)
        val emailAddresses = HashSet<EmailAddress>()
        eventStream.forEach {
            if (it is AuthorCreated) {
                emailAddresses.add(it.email)
            }
        }
        return emailAddresses.contains(email)
    }

    override fun get(id: AuthorId): Optional<Author> {
        val eventStream = eventStore.query(id)
        if (eventStream.isEmpty()) {
            return Optional.empty()
        }
        return Optional.of(Author(eventStream))
    }

    override fun save(aggregate: Author) {
        if (aggregate.noStateChanges()) {
            return
        }
        get(aggregate.getId()).ifPresent {
            if (aggregate.differsFromOrigin(it)) {
                throw RuntimeException("Optimistic Locking Exception")
            }
        }
        eventStore.append(aggregate.getMutations())
    }
}