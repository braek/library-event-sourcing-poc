package be.koder.library.domain.author

import be.koder.library.vocabulary.author.AuthorId

interface AuthorService {
    fun exists(authorId: AuthorId): Boolean
}