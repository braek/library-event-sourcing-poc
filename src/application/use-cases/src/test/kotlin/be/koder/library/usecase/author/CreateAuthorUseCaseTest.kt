package be.koder.library.usecase.author

import be.koder.library.api.author.CreateAuthorPresenter
import be.koder.library.domain.author.event.AuthorCreated
import be.koder.library.test.InMemoryAuthorRepository
import be.koder.library.test.MockEventStore
import be.koder.library.test.MockEventStreamPublisher
import be.koder.library.test.TestUtils
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.EmailAddress
import be.koder.library.vocabulary.author.FirstName
import be.koder.library.vocabulary.author.LastName
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

@DisplayName("Given a use case to create Authors")
class CreateAuthorUseCaseTest {

    private val eventStore = MockEventStore()
    private val authorRepository = InMemoryAuthorRepository(eventStore)
    private val eventStreamPublisher = MockEventStreamPublisher()
    private val useCase = CreateAuthorUseCase(authorRepository, eventStreamPublisher, authorRepository)

    @Nested
    @DisplayName("when Author created successfully")
    inner class TestHappyFlow : CreateAuthorPresenter {

        private val firstName = FirstName("John")
        private val lastName = LastName("Doe")
        private val email = EmailAddress("john.doe@sandbox.com")
        private var createdCalled = false
        private var authorId: AuthorId? = null

        @BeforeEach
        fun setup() {
            useCase.createAuthor(firstName, lastName, email, this)
        }

        @Test
        @DisplayName("it should provide feedback")
        fun feedbackProvided() {
            assertNotNull(authorId)
            assertTrue(createdCalled)
        }

        @Test
        @DisplayName("it should publish an event")
        fun eventsPublished() {
            assertThat(eventStreamPublisher.getPublishedEvents())
                .usingRecursiveComparison()
                .ignoringFields("id", "occurredOn")
                .isEqualTo(listOf(AuthorCreated(authorId!!, firstName, lastName, email)))
        }

        override fun created(authorId: AuthorId) {
            this.createdCalled = true
            this.authorId = authorId
        }

        override fun emailAlreadyInUse(email: EmailAddress) {
            TestUtils.fail()
        }
    }
}