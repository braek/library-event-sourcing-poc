package be.koder.library.api.author

import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.EmailAddress
import be.koder.library.vocabulary.author.FirstName
import be.koder.library.vocabulary.author.LastName

interface ModifyAuthor {
    fun modifyAuthor(authorId: AuthorId, firstName: FirstName, lastName: LastName, email: EmailAddress, presenter: ModifyAuthorPresenter)
}