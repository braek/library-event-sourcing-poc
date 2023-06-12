package be.koder.library.usecase.author

import be.koder.library.api.author.RemoveAuthorPresenter
import be.koder.library.domain.author.Author
import be.koder.library.domain.author.event.AuthorCreated
import be.koder.library.domain.author.event.AuthorModified
import be.koder.library.domain.author.event.AuthorRemoved
import be.koder.library.domain.event.EventStream
import be.koder.library.test.InMemoryAuthorRepository
import be.koder.library.test.InMemoryEventStore
import be.koder.library.test.InMemoryEventStreamPublisher
import be.koder.library.test.TestUtils
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.EmailAddress
import be.koder.library.vocabulary.author.FirstName
import be.koder.library.vocabulary.author.LastName
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Given a use case to remove Authors")
class RemoveAuthorUseCaseTest {

    private val eventStore = InMemoryEventStore()
    private val authorRepository = InMemoryAuthorRepository(eventStore)
    private val eventStreamPublisher = InMemoryEventStreamPublisher()
    private val useCase = RemoveAuthorUseCase(authorRepository, eventStreamPublisher)

    @Nested
    @DisplayName("when Author removed successfully")
    inner class TestHappyFlow : RemoveAuthorPresenter {

        private var removedAuthorId: AuthorId? = null
        private var removedCalled = false
        private val authorId = AuthorId.createNew()

        @BeforeEach
        fun setup() {
            eventStore.append(
                EventStream(
                    AuthorCreated(
                        authorId,
                        FirstName("Bruce"),
                        LastName("Wayne"),
                        EmailAddress("batman@gothamcity.com")
                    )
                )
            )
            useCase.removeAuthor(authorId, this)
        }

        @Test
        @DisplayName("it should provide feedback")
        fun feedbackProvided() {
            assertTrue(removedCalled)
            assertThat(removedAuthorId).isEqualTo(authorId)
        }

        @Test
        @DisplayName("it should publish an event")
        fun eventPublished() {
            assertThat(eventStreamPublisher.getPublishedEvents()).usingRecursiveComparison().ignoringFields("id", "occurredOn").isEqualTo(
                listOf(
                    AuthorRemoved(authorId)
                )
            )
        }

        @Test
        @DisplayName("it should change the state")
        fun stateChanged() {
            assertThat(authorRepository.get(authorId)).isEmpty()
        }

        override fun removed(authorId: AuthorId) {
            removedCalled = true
            removedAuthorId = authorId
        }

        override fun authorNotFound() {
            TestUtils.fail()
        }
    }
}