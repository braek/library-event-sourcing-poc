package be.koder.library.usecase.author

import be.koder.library.api.author.RemoveAuthor
import be.koder.library.api.author.RemoveAuthorPresenter
import be.koder.library.domain.author.AuthorRepository
import be.koder.library.domain.event.EventStreamPublisher
import be.koder.library.usecase.UseCase
import be.koder.library.usecase.author.command.RemoveAuthorCommand
import be.koder.library.vocabulary.author.AuthorId

class RemoveAuthorUseCase(
    private val authorRepository: AuthorRepository, private val eventStreamPublisher: EventStreamPublisher
) : RemoveAuthor, UseCase<RemoveAuthorCommand, RemoveAuthorPresenter> {

    override fun removeAuthor(authorId: AuthorId, presenter: RemoveAuthorPresenter) {
        execute(RemoveAuthorCommand(authorId), presenter)
    }

    override fun execute(command: RemoveAuthorCommand, presenter: RemoveAuthorPresenter) {
        authorRepository.get(command.authorId).ifPresentOrElse(
            {
                it.remove()
                authorRepository.save(it)
                eventStreamPublisher.publish(it.getMutations())
                presenter.removed(command.authorId)
            }, presenter::authorNotFound
        )
    }
}