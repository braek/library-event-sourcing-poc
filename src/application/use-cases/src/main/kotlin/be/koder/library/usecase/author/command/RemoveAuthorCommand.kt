package be.koder.library.usecase.author.command

import be.koder.library.domain.cqrs.Command
import be.koder.library.vocabulary.author.AuthorId

data class RemoveAuthorCommand(val authorId: AuthorId) : Command