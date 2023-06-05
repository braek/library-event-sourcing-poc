package be.koder.library.test

import be.koder.library.domain.author.Author
import be.koder.library.domain.author.AuthorRepository
import be.koder.library.domain.author.EmailService
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.EmailAddress
import java.util.*

class MockAuthorRepository(private val eventStore: MockEventStore) : AuthorRepository, EmailService {

    override fun exists(email: EmailAddress): Boolean {
        TODO("Not yet implemented")
    }

    override fun get(id: AuthorId): Optional<Author> {
        TODO("Not yet implemented")
    }

    override fun save(aggregate: Author) {
        eventStore.append(aggregate.getMutations())
    }
}