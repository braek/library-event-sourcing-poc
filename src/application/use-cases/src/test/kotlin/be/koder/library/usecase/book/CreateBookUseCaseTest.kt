package be.koder.library.usecase.book

import be.koder.library.api.book.CreateBookPresenter
import be.koder.library.domain.book.event.BookCreated
import be.koder.library.test.*
import be.koder.library.vocabulary.book.BookId
import be.koder.library.vocabulary.book.Isbn
import be.koder.library.vocabulary.book.Title
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Given a use case to create Books")
class CreateBookUseCaseTest {

    private val eventStore = InMemoryEventStore()
    private val bookRepository = InMemoryBookRepository(eventStore)
    private val eventPublisher = InMemoryEventPublisher()
    private val useCase = CreateBookUseCase(bookRepository, eventPublisher, bookRepository)

    @Nested
    @DisplayName("when Book created successfully")
    inner class TestHappyFlow : CreateBookPresenter {

        private var createdCalled: Boolean = false
        private var createdBookId: BookId? = null
        private val title = Title.fromString("Domain-Driven Design")
        private val isbn = Isbn.fromString("9780321125217")

        @BeforeEach
        fun setup() {
            useCase.createBook(
                title, isbn, this
            )
        }

        @Test
        @DisplayName("it should provide feedback")
        fun feedbackProvided() {
            assertTrue(createdCalled)
        }

        @Test
        @DisplayName("it should publish an event")
        fun eventPublished() {
            assertThat(eventPublisher.getPublishedEvents()).usingRecursiveComparison().ignoringFields("id", "occurredOn")
                .isEqualTo(listOf(BookCreated(createdBookId!!, title, isbn)))
        }

        @Test
        @DisplayName("it should be saved")
        fun bookSaved() {
            val book = bookRepository.get(createdBookId!!).map { it.takeSnapshot() }.orElseThrow()
            assertThat(book.id).isNotNull()
            assertThat(book.title).isEqualTo(title)
            assertThat(book.isbn).isEqualTo(isbn)
        }

        override fun created(bookId: BookId) {
            createdCalled = true
            createdBookId = bookId
        }

        override fun isbnAlreadyInUse(isbn: Isbn) {
            TestUtils.fail()
        }
    }

    @Nested
    @DisplayName("when ISBN already in use")
    inner class TestWhenIsbnAlreadyInUse : CreateBookPresenter {

        private var isbnAlreadyInUseCalled: Boolean = false
        private val title = Title.fromString("Domain-Driven Design")
        private val isbn = Isbn.fromString("9780321125217")

        @BeforeEach
        fun setup() {
            useCase.createBook(title, isbn, MockCreateBookPresenter())
            eventPublisher.clear()
            useCase.createBook(title, isbn, this)
        }

        @Test
        @DisplayName("it should provide feedback")
        fun feedbackProvided() {
            assertTrue(isbnAlreadyInUseCalled)
        }

        @Test
        @DisplayName("it should not publish events")
        fun noEventsPublished() {
            assertThat(eventPublisher.getPublishedEvents()).isEmpty()
        }

        override fun isbnAlreadyInUse(isbn: Isbn) {
            isbnAlreadyInUseCalled = true
        }

        override fun created(bookId: BookId) {
            TestUtils.fail()
        }
    }
}