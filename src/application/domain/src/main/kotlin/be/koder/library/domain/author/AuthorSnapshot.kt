package be.koder.library.domain.author

import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.EmailAddress
import be.koder.library.vocabulary.author.FirstName
import be.koder.library.vocabulary.author.LastName

data class AuthorSnapshot(
    val id: AuthorId,
    val firstName: FirstName,
    val lastName: LastName,
    val emailAddress: EmailAddress
)