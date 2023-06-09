package be.koder.library.rdbms

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.test.context.ContextConfiguration

@SpringBootApplication(
    exclude = [DataSourceAutoConfiguration::class]
)
@ContextConfiguration(
    classes = [RdbmsConfig::class]
)
open class TestApp {
    fun main(args: Array<String>) {
        runApplication<TestApp>(*args)
    }
}