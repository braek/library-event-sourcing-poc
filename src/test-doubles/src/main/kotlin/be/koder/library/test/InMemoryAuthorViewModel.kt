package be.koder.library.test

import be.koder.library.query.AuthorViewModel
import be.koder.library.vocabulary.author.AuthorListItem

class InMemoryAuthorViewModel : AuthorViewModel {
    override fun listAuthors(): List<AuthorListItem> {
        return emptyList()
    }
}