package be.koder.library.api.author

import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.EmailAddress

interface CreateAuthorPresenter {
    fun created(authorId: AuthorId)

    fun emailAddressAlreadyInUse(email: EmailAddress)
}