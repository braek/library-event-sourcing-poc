package be.koder.library.rdbms

import be.koder.library.domain.event.EventStore
import be.koder.library.rdbms.event.RdbmsEventStore
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.conf.MappedSchema
import org.jooq.conf.RenderMapping
import org.jooq.conf.Settings
import org.jooq.impl.DefaultDSLContext
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
open class TestBeanConfig : ApplicationContextInitializer<ConfigurableApplicationContext> {

    override fun initialize(context: ConfigurableApplicationContext) {
        TestPropertyValues.of(
            "spring.datasource.url=jdbc:postgresql://localhost:6000/sandbox",
            "spring.datasource.username=sandbox",
            "spring.datasource.password=sandbox",
            "spring.datasource.driver-class-name=org.postgresql.Driver",
            "spring.jpa.hibernate.ddl-auto=none"
        ).applyTo(context.environment)
    }

    @Bean
    open fun dslContext(dataSource: DataSource): DSLContext {
        return DefaultDSLContext(
            dataSource,
            SQLDialect.POSTGRES,
            Settings().withRenderMapping(
                RenderMapping().withSchemata(
                    MappedSchema().withInput(Sandbox.SANDBOX.name)
                )
            )
        )
    }

    @Bean
    open fun eventStore(dslContext: DSLContext): EventStore {
        return RdbmsEventStore(dslContext)
    }
}