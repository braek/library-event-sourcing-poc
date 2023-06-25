package be.koder.library.usecase.book

import be.koder.library.api.book.UnlinkAuthorFromBook
import be.koder.library.test.InMemoryAuthorRepository
import be.koder.library.test.InMemoryBookRepository
import be.koder.library.test.InMemoryEventPublisher
import be.koder.library.test.InMemoryEventStore
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName

@DisplayName("Given a use case to unlink an Author from a Book")
class UnlinkAuthorFromBookUseCaseTest {

    private val eventStore: InMemoryEventStore = InMemoryEventStore()
    private val eventPublisher: InMemoryEventPublisher = InMemoryEventPublisher()
    private val authorRepository: InMemoryAuthorRepository = InMemoryAuthorRepository(eventStore)
    private val bookRepository: InMemoryBookRepository = InMemoryBookRepository(eventStore)
    private val useCase: UnlinkAuthorFromBook = UnlinkAuthorFromBookUseCase(bookRepository, eventPublisher, authorRepository)

}