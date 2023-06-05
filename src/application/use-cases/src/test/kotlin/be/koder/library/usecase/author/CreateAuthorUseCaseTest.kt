package be.koder.library.usecase.author

import be.koder.library.api.author.CreateAuthorPresenter
import be.koder.library.test.InMemoryAuthorRepository
import be.koder.library.test.MockEventStore
import be.koder.library.test.MockEventStreamPublisher
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.EmailAddress
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

@DisplayName("Given a use case to create Authors")
class CreateAuthorUseCaseTest {

    private val eventStore = MockEventStore()
    private val authorRepository = InMemoryAuthorRepository(eventStore)
    private val eventStreamPublisher = MockEventStreamPublisher()
    private val useCase = CreateAuthorUseCase(authorRepository, eventStreamPublisher, authorRepository)

    @Nested
    @DisplayName("when Author created successfully")
    inner class TestHappyFlow : CreateAuthorPresenter {

        @BeforeEach
        fun setup() {

        }

        override fun created(authorId: AuthorId) {
            TODO("Not yet implemented")
        }

        override fun emailAlreadyInUse(email: EmailAddress) {
            TODO("Not yet implemented")
        }

    }
}