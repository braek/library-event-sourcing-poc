package be.koder.library.test

import be.koder.library.domain.author.Author
import be.koder.library.domain.author.AuthorRepository
import be.koder.library.domain.author.EmailService
import be.koder.library.domain.author.event.AuthorCreated
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.EmailAddress
import java.util.*

class InMemoryAuthorRepository(private val eventStore: MockEventStore) : AuthorRepository, EmailService {

    override fun alreadyInUse(emailAddress: EmailAddress): Boolean {
        val eventStream = eventStore.query(AuthorCreated::class)
        val stack = HashSet<EmailAddress>()
        eventStream.stream()
            .filter { it is AuthorCreated }
            .map { it as AuthorCreated }
            .forEach { stack.add(it.emailAddress) }
        return stack.contains(emailAddress)
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