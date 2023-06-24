package be.koder.library.usecase.book

import be.koder.library.api.book.LinkAuthorToBook
import be.koder.library.api.book.LinkAuthorToBookPresenter
import be.koder.library.domain.author.event.AuthorCreated
import be.koder.library.domain.book.event.AuthorLinkedToBook
import be.koder.library.domain.book.event.BookCreated
import be.koder.library.domain.event.EventStream
import be.koder.library.test.*
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.EmailAddress
import be.koder.library.vocabulary.author.FirstName
import be.koder.library.vocabulary.author.LastName
import be.koder.library.vocabulary.book.BookId
import be.koder.library.vocabulary.book.Isbn
import be.koder.library.vocabulary.book.Title
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Given a use case to link Authors to Books")
class LinkAuthorToBookUseCaseTest {

    private val eventStore: InMemoryEventStore = InMemoryEventStore()
    private val eventPublisher: InMemoryEventPublisher = InMemoryEventPublisher()
    private val authorRepository: InMemoryAuthorRepository = InMemoryAuthorRepository(eventStore)
    private val bookRepository: InMemoryBookRepository = InMemoryBookRepository(eventStore)
    private val useCase: LinkAuthorToBook = LinkAuthorToBookUseCase(authorRepository, bookRepository, eventPublisher)

    @Nested
    @DisplayName("when existing Author linked to existing Book")
    inner class TestHappyFlow : LinkAuthorToBookPresenter {

        private var linkedCalled = false
        private val ericEvans = AuthorId.createNew()
        private val vaughnVernon = AuthorId.createNew()

        private val bookId = BookId.createNew()
        private val bookTitle = Title.fromString("Domain-Driven Design Distilled")
        private val bookIsbn = Isbn.fromString("9780134434421")

        @BeforeEach
        fun setup() {
            eventStore.append(
                EventStream(
                    AuthorCreated(
                        vaughnVernon,
                        FirstName("Vaughn"),
                        LastName("Vernon"),
                        EmailAddress("vaughn.vernon@ddd.com")
                    ),
                    AuthorCreated(
                        ericEvans,
                        FirstName("Eric"),
                        LastName("Evans"),
                        EmailAddress("eric.evans@ddd.com")
                    ),
                    BookCreated(
                        bookId,
                        bookTitle,
                        bookIsbn,
                        vaughnVernon
                    )
                )
            )
            useCase.linkAuthorToBook(ericEvans, bookId, this)
        }

        @Test
        @DisplayName("it should provide feedback")
        fun feedbackProvided() {
            assertTrue(linkedCalled)
        }

        @Test
        @DisplayName("it should publish an event")
        fun eventPublished() {
            eventPublisher.verifyPublishedEvents(
                AuthorLinkedToBook(
                    ericEvans,
                    bookId
                )
            )
        }

        @Test
        @DisplayName("it should be saved")
        fun bookSaved() {
            val book = bookRepository.get(bookId).map { it.takeSnapshot() }.orElseThrow()
            assertThat(book.id).isEqualTo(bookId)
            assertThat(book.title).isEqualTo(bookTitle)
            assertThat(book.isbn).isEqualTo(bookIsbn)
            assertThat(book.authors).containsExactlyInAnyOrder(ericEvans, vaughnVernon)
        }

        override fun linked(author: AuthorId, book: BookId) {
            linkedCalled = true;
        }

        override fun authorDoesNotExist(author: AuthorId) {
            TestUtils.fail()
        }

        override fun bookDoesNotExist(book: BookId) {
            TestUtils.fail()
        }
    }
}