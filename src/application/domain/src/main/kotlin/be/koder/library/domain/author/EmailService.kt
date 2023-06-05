package be.koder.library.domain.author

import be.koder.library.vocabulary.author.EmailAddress

interface EmailService {
    fun exists(email: EmailAddress): Boolean
}