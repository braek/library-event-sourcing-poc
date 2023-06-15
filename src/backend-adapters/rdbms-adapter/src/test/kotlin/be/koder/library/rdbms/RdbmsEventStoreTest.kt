package be.koder.library.rdbms

import be.koder.library.domain.event.Event
import be.koder.library.domain.event.EventStore
import be.koder.library.domain.event.EventStream
import be.koder.library.domain.event.EventStreamQuery
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@DisplayName("Given an RDBMS event store")
@ContextConfiguration(initializers = [TestBeanConfig::class])
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
            val persistedEventStream = eventStore.query(EventStreamQuery(
                event.tags,
                setOf(
                    event.javaClass.simpleName
                )
            ))
            assertThat(persistedEventStream).isEqualTo(eventStream)
        }
    }
}