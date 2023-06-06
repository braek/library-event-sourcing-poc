package be.koder.library.api.author

import be.koder.library.vocabulary.author.AuthorId

interface ModifyAuthorPresenter {
    fun modified(authorId: AuthorId)
}