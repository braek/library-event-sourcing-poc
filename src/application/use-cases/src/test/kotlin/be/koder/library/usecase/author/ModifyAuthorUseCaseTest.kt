package be.koder.library.usecase.author

import be.koder.library.api.author.ModifyAuthorPresenter
import be.koder.library.domain.author.event.AuthorCreated
import be.koder.library.domain.author.event.AuthorModified
import be.koder.library.domain.event.EventStream
import be.koder.library.test.InMemoryAuthorRepository
import be.koder.library.test.InMemoryEventStore
import be.koder.library.test.InMemoryEventPublisher
import be.koder.library.test.TestUtils
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.EmailAddress
import be.koder.library.vocabulary.author.FirstName
import be.koder.library.vocabulary.author.LastName
import be.koder.library.vocabulary.event.EventId
import be.koder.library.vocabulary.time.Timestamp
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Given a use case to modify Authors")
class ModifyAuthorUseCaseTest {

    private val eventStore = InMemoryEventStore()
    private val authorRepository = InMemoryAuthorRepository(eventStore)
    private val eventStreamPublisher = InMemoryEventPublisher()
    private val useCase = ModifyAuthorUseCase(authorRepository, authorRepository, eventStreamPublisher)

    @Nested
    @DisplayName("when Author modified successfully")
    inner class TestHappyFlow : ModifyAuthorPresenter {

        private var authorId = AuthorId.createNew()
        private val firstName = FirstName("John")
        private val lastName = LastName("Doe")
        private val emailAddress = EmailAddress("john.doe@sandbox.com")
        private var modifiedCalled = false

        @BeforeEach
        fun setup() {
            eventStore.append(
                EventStream(
                    AuthorCreated(
                        EventId.createNew(), Timestamp.now(), authorId, FirstName("Bruce"), LastName("Wayne"), EmailAddress("batman@gothamcity.com")
                    )
                ),
            )
            useCase.modifyAuthor(authorId, firstName, lastName, emailAddress, this)
        }

        @Test
        @DisplayName("it should provide feedback")
        fun feedbackProvided() {
            assertTrue(modifiedCalled)
        }

        @Test
        @DisplayName("it should change the state")
        fun stateChanged() {
            var actualState = authorRepository.get(authorId).map { it.takeSnapshot() }.orElseThrow()
            assertThat(actualState.id).isEqualTo(authorId)
            assertThat(actualState.firstName).isEqualTo(firstName)
            assertThat(actualState.lastName).isEqualTo(lastName)
            assertThat(actualState.emailAddress).isEqualTo(emailAddress)
        }

        @Test
        @DisplayName("it should publish an event")
        fun eventPublished() {
            assertThat(eventStreamPublisher.getPublishedEvents()).usingRecursiveComparison().ignoringFields("id", "occurredOn").isEqualTo(
                listOf(
                    AuthorModified(EventId.createNew(), Timestamp.now(), authorId, firstName, lastName, emailAddress)
                )
            )
        }

        override fun modified(authorId: AuthorId) {
            modifiedCalled = true
        }

        override fun emailAddressAlreadyInUse(emailAddress: EmailAddress) {
            TestUtils.fail()
        }

        override fun authorNotFound() {
            TestUtils.fail()
        }
    }

    @Nested
    @DisplayName("when Author not found")
    inner class TestWhenAuthorNotFound : ModifyAuthorPresenter {

        private var authorNotFoundCalled = false

        @BeforeEach
        fun setup() {
            useCase.modifyAuthor(
                AuthorId.createNew(), FirstName("Bruce"), LastName("Wayne"), EmailAddress("batman@gothamcity.com"), this
            )
        }

        @Test
        @DisplayName("it should provide feedback")
        fun feedbackProvided() {
            assertTrue(authorNotFoundCalled)
        }

        @Test
        @DisplayName("it should not publish events")
        fun noEventsPublished() {
            assertThat(eventStreamPublisher.getPublishedEvents()).isEmpty()
        }

        override fun modified(authorId: AuthorId) {
            TestUtils.fail()
        }

        override fun emailAddressAlreadyInUse(emailAddress: EmailAddress) {
            TestUtils.fail()
        }

        override fun authorNotFound() {
            authorNotFoundCalled = true
        }
    }

    @Nested
    @DisplayName("when e-mail addresss already in use")
    inner class TestWhenEmailAddressAlreadyInUse : ModifyAuthorPresenter {

        private var emailAddressAlreadyInUseCalled = false
        private var authorId = AuthorId.createNew()
        private val firstName = FirstName("Bruce")
        private val lastName = LastName("Wayne")
        private val emailAddress = EmailAddress("joker@gothamcity.com")

        @BeforeEach
        fun setup() {
            eventStore.append(
                EventStream(
                    AuthorCreated(
                        EventId.createNew(),
                        Timestamp.now(),
                        authorId,
                        firstName,
                        lastName,
                        EmailAddress("batman@gothamcity.com")
                    ),
                    AuthorCreated(
                        EventId.createNew(),
                        Timestamp.now(),
                        AuthorId.createNew(),
                        FirstName("Arthur"),
                        LastName("Fleck"),
                        emailAddress
                    )
                ),
            )
            useCase.modifyAuthor(authorId, firstName, lastName, emailAddress, this)
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

        override fun modified(authorId: AuthorId) {
            TestUtils.fail()
        }

        override fun emailAddressAlreadyInUse(emailAddress: EmailAddress) {
            emailAddressAlreadyInUseCalled = true
        }

        override fun authorNotFound() {
            TestUtils.fail()
        }
    }
}