package be.koder.library.usecase.author

import be.koder.library.domain.cqrs.Command
import be.koder.library.vocabulary.author.EmailAddress
import be.koder.library.vocabulary.author.FirstName
import be.koder.library.vocabulary.author.LastName

data class CreateAuthorCommand(val firstName: FirstName, val lastName: LastName, val email: EmailAddress) : Command
