package be.koder.library.rdbms.event

import be.koder.library.rdbms.TestBeanConfig
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@SpringBootTest
@DisplayName("Given an RDBMS event store")
@ContextConfiguration(initializers = [TestBeanConfig::class])
class RdbmsEventStoreTest(@Autowired private val eventStore: RdbmsEventStore) {

    @Test
    fun testIt() {
        assertTrue(false)
    }
}