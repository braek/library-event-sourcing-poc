package be.koder.library.api.author

import be.koder.library.vocabulary.author.AuthorId

interface RemoveAuthor {

    fun removeAuthor(authorId: AuthorId, presenter: RemoveAuthorPresenter)
}