package be.koder.library.usecase

import be.koder.library.domain.cqrs.Command

interface UseCase<COMMAND : Command, PRESENTER> {
    fun execute(command: COMMAND, presenter: PRESENTER)
}