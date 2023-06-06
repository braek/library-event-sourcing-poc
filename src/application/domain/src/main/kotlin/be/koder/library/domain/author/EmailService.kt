package be.koder.library.domain.author

import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.EmailAddress

interface EmailService {

    fun alreadyInUse(emailAddress: EmailAddress, exclude: AuthorId): Boolean

    fun alreadyInUse(emailAddress: EmailAddress): Boolean
}