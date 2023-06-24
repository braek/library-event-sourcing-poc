package be.koder.library.rdbms

import be.koder.library.domain.author.Author
import be.koder.library.domain.event.Event
import be.koder.library.domain.event.EventStore
import be.koder.library.domain.event.EventStream
import be.koder.library.domain.event.EventStreamChangedException
import be.koder.library.vocabulary.author.EmailAddress
import be.koder.library.vocabulary.author.FirstName
import be.koder.library.vocabulary.author.LastName
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@DisplayName("Given an RDBMS event store")
class RdbmsEventStoreTest @Autowired constructor(private val eventStore: EventStore) {

    @Nested
    @DisplayName("when storing a single Event")
    inner class TestWhenStoringSingleEvent {

        @ArgumentsSource(EventParams::class)
        @ParameterizedTest
        @DisplayName("it should be stored successfully")
        fun eventStored(event: Event) {
            val eventStream = EventStream(event)
            eventStore.append(eventStream)
            val persistedEventStream = eventStore.query(event.javaClass.simpleName)
            assertThat(persistedEventStream).isEqualTo(eventStream)
        }
    }

    @Nested
    @DisplayName("when simulating optimistic locking")
    inner class TestOptimisticLocking {

        @Test
        fun testIt() {

            // Create a new author
            val author = Author.create(
                FirstName("Bruce"),
                LastName("Wayne"),
                EmailAddress("batman@gothamcity.com")
            )
            val authorId = author.getId()
            eventStore.appendMutations(author)

            // First fetch: don't do anything yet
            val first = Author(eventStore.query(authorId))

            // Second fetch: manipulate and save
            val second = Author(eventStore.query(authorId))
            second.remove()
            eventStore.appendMutations(second)

            // Trigger optimistic locking
            assertThrows<EventStreamChangedException> {
                first.remove()
                eventStore.appendMutations(first)
            }
        }
    }
}