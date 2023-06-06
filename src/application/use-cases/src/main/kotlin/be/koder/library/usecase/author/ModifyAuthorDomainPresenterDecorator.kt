package be.koder.library.usecase.author

import be.koder.library.api.author.ModifyAuthorPresenter
import be.koder.library.domain.author.presenter.ModifyAuthorDomainPresenter
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.EmailAddress

class ModifyAuthorDomainPresenterDecorator(private val presenter: ModifyAuthorPresenter) : ModifyAuthorDomainPresenter {

    override fun modified(authorId: AuthorId) {
        presenter.modified(authorId)
    }

    override fun emailAddressAlreadyInUse(emailAddress: EmailAddress) {
        presenter.emailAddressAlreadyInUse(emailAddress)
    }
}