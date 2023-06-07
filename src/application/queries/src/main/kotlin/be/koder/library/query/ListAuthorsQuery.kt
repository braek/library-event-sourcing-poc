package be.koder.library.query

import be.koder.library.api.author.ListAuthors
import be.koder.library.vocabulary.author.AuthorListItem

class ListAuthorsQuery(private val viewModel: AuthorViewModel) : ListAuthors {
    override fun listAuthors(): List<AuthorListItem> {
        return viewModel.listAuthors()
    }
}