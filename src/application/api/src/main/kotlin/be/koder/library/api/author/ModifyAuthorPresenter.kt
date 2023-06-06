package be.koder.library.api.author

import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.EmailAddress

interface ModifyAuthorPresenter {

    fun modified(authorId: AuthorId)

    fun emailAddressAlreadyInUse(emailAddress: EmailAddress)

    fun authorNotFound()
}