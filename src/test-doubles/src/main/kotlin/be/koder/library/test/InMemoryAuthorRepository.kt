package be.koder.library.test

import be.koder.library.domain.author.Author
import be.koder.library.domain.author.AuthorRepository
import be.koder.library.domain.author.EmailService
import be.koder.library.domain.author.event.AuthorCreated
import be.koder.library.domain.author.event.AuthorModified
import be.koder.library.domain.author.event.AuthorRemoved
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.EmailAddress
import java.util.*

class InMemoryAuthorRepository(private val eventStore: InMemoryEventStore) : AuthorRepository, EmailService {

    override fun alreadyInUse(emailAddress: EmailAddress, exclude: AuthorId): Boolean {
        val stack = buildEmailAddressStack()
        stack.remove(exclude)
        return stack.values.contains(emailAddress)
    }

    override fun alreadyInUse(emailAddress: EmailAddress): Boolean {
        return buildEmailAddressStack().values.contains(emailAddress)
    }

    private fun buildEmailAddressStack(): MutableMap<AuthorId, EmailAddress> {
        val stack: MutableMap<AuthorId, EmailAddress> = mutableMapOf()
        val eventStream = eventStore.queryByTypes(
            AuthorCreated::class.simpleName!!,
            AuthorModified::class.simpleName!!
        )
        eventStream.stream()
            .filter { it is AuthorCreated }
            .map { it as AuthorCreated }
            .forEach { stack[it.authorId()] = it.emailAddress }
        return stack
    }

    override fun get(id: AuthorId): Optional<Author> {
        val eventStream = eventStore.query(id)
        if (eventStream.isEmpty() || eventStream.containsEventOfType(AuthorRemoved::class.simpleName!!)) {
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