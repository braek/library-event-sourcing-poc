package be.koder.library.rdbms

import be.koder.library.domain.author.event.AuthorCreated
import be.koder.library.domain.event.EventStore
import be.koder.library.domain.event.EventStream
import be.koder.library.rdbms.event.RdbmsEventStore
import be.koder.library.vocabulary.author.AuthorId
import be.koder.library.vocabulary.author.EmailAddress
import be.koder.library.vocabulary.author.FirstName
import be.koder.library.vocabulary.author.LastName
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@DisplayName("Given an RDBMS event store")
@ContextConfiguration(initializers = [TestBeanConfig::class])
class RdbmsEventStoreTest @Autowired constructor(private val eventStore: EventStore) {

    @Test
    fun testIt() {
        eventStore.append(EventStream(
            AuthorCreated(
                AuthorId.createNew(),
                FirstName("Bruce"),
                LastName("Wayne"),
                EmailAddress("batman@gothamcity.com")
            )
        ))
        assertTrue(false)
    }
}