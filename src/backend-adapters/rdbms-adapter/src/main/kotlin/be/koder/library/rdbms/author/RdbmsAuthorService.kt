package be.koder.library.rdbms.author

import be.koder.library.domain.author.AuthorService
import be.koder.library.rdbms.event.RdbmsEventStore
import be.koder.library.vocabulary.author.AuthorId

class RdbmsAuthorService(private val eventStore: RdbmsEventStore) : AuthorService {

    override fun exists(authorId: AuthorId): Boolean {
        TODO("Not yet implemented")
    }
}