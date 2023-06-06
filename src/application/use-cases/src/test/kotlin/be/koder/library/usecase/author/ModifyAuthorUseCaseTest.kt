package be.koder.library.usecase.author

import be.koder.library.api.author.ModifyAuthorPresenter
import be.koder.library.domain.author.event.AuthorCreated
import be.koder.library.domain.event.EventStream
import be.koder.library.test.InMemoryAuthorRepository
import be.koder.library.test.InMemoryEventStore
import be.koder.library.test.InMemoryEventStreamPublisher
import be.koder.library.test.TestUtils
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.EmailAddress
import be.koder.library.vocabulary.author.FirstName
import be.koder.library.vocabulary.author.LastName
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Given a use case to modify Authors")
class ModifyAuthorUseCaseTest {

    private val eventStore = InMemoryEventStore()
    private val authorRepository = InMemoryAuthorRepository(eventStore)
    private val eventStreamPublisher = InMemoryEventStreamPublisher()
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
                        authorId, FirstName("Bruce"), LastName("Wayne"), EmailAddress("batman@gothamcity.com")
                    )
                )
            )
            useCase.modifyAuthor(authorId, firstName, lastName, emailAddress, this)
        }

        @Test
        @DisplayName("it should provide feedback")
        fun feedbackProvided() {
            assertTrue(modifiedCalled)
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
}