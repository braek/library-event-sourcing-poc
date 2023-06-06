package be.koder.library.test

import be.koder.library.api.author.CreateAuthorPresenter
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.EmailAddress

class MockCreateAuthorPresenter : CreateAuthorPresenter {

    override fun created(authorId: AuthorId) {}

    override fun emailAlreadyInUse(email: EmailAddress) {}
}