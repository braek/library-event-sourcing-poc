package be.koder.library.usecase.author.command

import be.koder.library.domain.cqrs.Command
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.EmailAddress
import be.koder.library.vocabulary.author.FirstName
import be.koder.library.vocabulary.author.LastName

data class ModifyAuthorCommand(
    val authorId: AuthorId,
    val firstName: FirstName,
    val lastName: LastName,
    val emailAddress: EmailAddress
) : Command