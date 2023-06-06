package be.koder.library.domain.author.presenter

import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.EmailAddress

interface ModifyAuthorDomainPresenter {

    fun modified(authorId: AuthorId)

    fun emailAddressAlreadyInUse(emailAddress: EmailAddress)
}