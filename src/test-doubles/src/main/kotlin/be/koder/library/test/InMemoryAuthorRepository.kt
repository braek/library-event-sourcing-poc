package be.koder.library.test

import be.koder.library.domain.author.Author
import be.koder.library.domain.author.AuthorRepository
import be.koder.library.domain.author.AuthorService
import be.koder.library.domain.author.EmailService
import be.koder.library.domain.author.event.AuthorCreated
import be.koder.library.domain.author.event.AuthorModified
import be.koder.library.domain.author.event.AuthorRemoved
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.EmailAddress
import java.util.*

class InMemoryAuthorRepository(private val eventStore: InMemoryEventStore) : AuthorRepository, AuthorService, EmailService {

    override fun alreadyInUse(emailAddress: EmailAddress, exclude: AuthorId): Boolean {
        val stack = buildEmailAddressStack()
        stack.remove(exclude)
        return stack.containsValue(emailAddress)
    }

    override fun alreadyInUse(emailAddress: EmailAddress): Boolean {
        return buildEmailAddressStack().containsValue(emailAddress)
    }

    private fun buildEmailAddressStack(): MutableMap<AuthorId, EmailAddress> {
        val stack: MutableMap<AuthorId, EmailAddress> = mutableMapOf()
        val eventStream = eventStore.queryByTypes(
            AuthorCreated::class.simpleName!!,
            AuthorModified::class.simpleName!!,
            AuthorRemoved::class.simpleName!!
        )
        eventStream.stream()
            .filter { it is AuthorCreated }
            .map { it as AuthorCreated }
            .forEach { stack[it.authorId] = it.emailAddress }
        eventStream.stream()
            .filter { it is AuthorModified }
            .map { it as AuthorModified }
            .forEach { stack[it.authorId] = it.emailAddress }
        eventStream.stream()
            .filter { it is AuthorRemoved }
            .map { it as AuthorRemoved }
            .forEach { stack.remove(it.authorId) }
        return stack
    }

    override fun exists(authorId: AuthorId): Boolean {
        val stack: MutableSet<AuthorId> = mutableSetOf()
        val eventStream = eventStore.queryByTypes(
            AuthorCreated::class.simpleName!!,
            AuthorRemoved::class.simpleName!!
        )
        eventStream.stream()
            .filter { it is AuthorCreated }
            .map { it as AuthorCreated }
            .forEach { stack.add(it.authorId) }
        eventStream.stream()
            .filter { it is AuthorRemoved }
            .map { it as AuthorRemoved }
            .forEach { stack.remove(it.authorId) }
        return stack.contains(authorId)
    }

    override fun get(id: AuthorId): Optional<Author> {
        val eventStream = eventStore.query(id)
        if (eventStream.isEmpty() || eventStream.containsEventOfType(AuthorRemoved::class.simpleName!!)) {
            return Optional.empty()
        }
        return Optional.of(Author(eventStream))
    }

    override fun save(aggregate: Author) {
        eventStore.save(aggregate)
    }
}