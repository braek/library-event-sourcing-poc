package be.koder.library.api.author

import be.koder.library.vocabulary.author.AuthorListItem

interface ListAuthors {
    fun listAuthors(): List<AuthorListItem>
}