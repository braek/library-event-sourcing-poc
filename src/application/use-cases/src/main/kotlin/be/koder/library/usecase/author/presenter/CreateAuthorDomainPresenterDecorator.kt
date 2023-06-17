package be.koder.library.usecase.author.presenter

import be.koder.library.api.author.CreateAuthorPresenter
import be.koder.library.domain.author.presenter.CreateAuthorDomainPresenter
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.EmailAddress

class CreateAuthorDomainPresenterDecorator(private val presenter: CreateAuthorPresenter) : CreateAuthorDomainPresenter {

    override fun created(authorId: AuthorId) {
        presenter.created(authorId)
    }

    override fun emailAddressAlreadyInUse(emailAddress: EmailAddress) {
        presenter.emailAddressAlreadyInUse(emailAddress)
    }
}