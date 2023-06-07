package be.koder.library.api.author

import be.koder.library.vocabulary.author.AuthorId

interface RemoveAuthorPresenter {

    fun removed(authorId: AuthorId)

    fun authorNotFound()
}