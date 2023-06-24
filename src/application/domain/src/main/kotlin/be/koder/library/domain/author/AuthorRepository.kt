package be.koder.library.domain.author

import be.koder.library.domain.repository.Repository
import be.koder.library.vocabulary.author.AuthorId

interface AuthorRepository : Repository<AuthorId, Author> {
    fun exists(authorId: AuthorId) : Boolean
}