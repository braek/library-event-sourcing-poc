package be.koder.library.domain.author.presenter

import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.EmailAddress

interface CreateAuthorDomainPresenter {

    fun created(authorId: AuthorId)

    fun emailAddressAlreadyInUse(emailAddress: EmailAddress)
}