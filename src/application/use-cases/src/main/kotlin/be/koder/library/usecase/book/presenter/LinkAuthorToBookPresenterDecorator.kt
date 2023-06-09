package be.koder.library.usecase.book.presenter

import be.koder.library.api.book.LinkAuthorToBookPresenter
import be.koder.library.domain.book.presenter.LinkAuthorToBookDomainPresenter
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.book.BookId

class LinkAuthorToBookPresenterDecorator(private val presenter: LinkAuthorToBookPresenter) : LinkAuthorToBookDomainPresenter {

    override fun linked(author: AuthorId, book: BookId) {
        presenter.authorLinked(author, book)
    }

    override fun authorAlreadyLinked(author: AuthorId) {
        presenter.authorAlreadyLinked(author)
    }

    override fun authorDoesNotExist(author: AuthorId) {
        presenter.authorDoesNotExist(author)
    }
}