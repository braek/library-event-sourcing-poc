package be.koder.library.query

import be.koder.library.vocabulary.author.AuthorListItem

interface AuthorViewModel {
    fun listAuthors(): List<AuthorListItem>
}