package be.koder.library.usecase.author

import be.koder.library.api.author.CreateAuthorPresenter
import be.koder.library.domain.author.event.AuthorCreated
import be.koder.library.domain.event.EventStream
import be.koder.library.test.*
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.EmailAddress
import be.koder.library.vocabulary.author.FirstName
import be.koder.library.vocabulary.author.LastName
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

@DisplayName("Given a use case to create Authors")
class CreateAuthorUseCaseTest {

    private val eventStore = InMemoryEventStore()
    private val authorRepository = InMemoryAuthorRepository(eventStore)
    private val eventStreamPublisher = InMemoryEventStreamPublisher()
    private val useCase = CreateAuthorUseCase(authorRepository, eventStreamPublisher, authorRepository)

    @Nested
    @DisplayName("when Author created successfully")
    inner class TestHappyFlow : CreateAuthorPresenter {

        private val firstName = FirstName("John")
        private val lastName = LastName("Doe")
        private val emailAddress = EmailAddress("john.doe@sandbox.com")
        private var createdCalled = false
        private var authorId: AuthorId? = null

        @BeforeEach
        fun setup() {
            useCase.createAuthor(firstName, lastName, emailAddress, this)
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
            assertThat(eventStreamPublisher.getPublishedEvents()).usingRecursiveComparison().ignoringFields("id", "occurredOn")
                .isEqualTo(listOf(AuthorCreated(authorId!!, firstName, lastName, emailAddress)))
        }

        override fun created(authorId: AuthorId) {
            this.createdCalled = true
            this.authorId = authorId
        }

        override fun emailAddressAlreadyInUse(emailAddress: EmailAddress) {
            TestUtils.fail()
        }
    }

    @Nested
    @DisplayName("when e-mail address already in use")
    inner class TestWhenEmailAddressAlreadyInUse : CreateAuthorPresenter {

        private val firstName = FirstName("John")
        private val lastName = LastName("Doe")
        private val emailAddress = EmailAddress("john.doe@sandbox.com")
        private var emailAddressAlreadyInUseCalled = false

        @BeforeEach
        fun setup() {
            eventStore.append(EventStream(AuthorCreated(AuthorId.createNew(), firstName, lastName, emailAddress)))
            useCase.createAuthor(firstName, lastName, emailAddress, this)
        }

        @Test
        @DisplayName("it should provide feedback")
        fun feedbackProvided() {
            assertTrue(emailAddressAlreadyInUseCalled)
        }

        @Test
        @DisplayName("it should not publish events")
        fun noEventsPublished() {
            assertThat(eventStreamPublisher.getPublishedEvents()).isEmpty()
        }

        override fun created(authorId: AuthorId) {
            TestUtils.fail()
        }

        override fun emailAddressAlreadyInUse(emailAddress: EmailAddress) {
            emailAddressAlreadyInUseCalled = true
        }
    }
}