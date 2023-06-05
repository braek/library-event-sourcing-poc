package be.koder.library.api.author

import be.koder.library.vocabulary.author.EmailAddress
import be.koder.library.vocabulary.author.FirstName
import be.koder.library.vocabulary.author.LastName

interface CreateAuthor {
    fun createAuthor(firstName: FirstName, lastName: LastName, email: EmailAddress, presenter: CreateAuthorPresenter)
}