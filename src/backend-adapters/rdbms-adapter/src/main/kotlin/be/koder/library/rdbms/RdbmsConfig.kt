package be.koder.library.rdbms

import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.conf.Settings
import org.jooq.impl.DataSourceConnectionProvider
import org.jooq.impl.DefaultConfiguration
import org.jooq.impl.DefaultDSLContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.jooq.SpringTransactionProvider
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@ConditionalOnProperty(name = ["be.koder.library.rdbms.enabled"], havingValue = "true")
@EnableTransactionManagement
open class RdbmsConfig {

    @Bean
    open fun dataSource(
        @Value("\${be.koder.library.rdbms.driver-class-name}") driverClassName: String,
        @Value("\${be.koder.library.rdbms.url}") url: String,
        @Value("\${be.koder.library.rdbms.username}") username: String,
        @Value("\${be.koder.library.rdbms.password}") password: String
    ): DataSource {
        return DataSourceBuilder.create().driverClassName(driverClassName).url(url).username(username).password(password).build()
    }

    @Bean
    open fun transactionManager(dataSource: DataSource): PlatformTransactionManager {
        return DataSourceTransactionManager(dataSource)
    }

    @Bean
    open fun connectionProvider(dataSource: DataSource): DataSourceConnectionProvider {
        return DataSourceConnectionProvider(TransactionAwareDataSourceProxy(dataSource))
    }

    @Bean
    open fun dsl(connectionProvider: DataSourceConnectionProvider, transactionManager: PlatformTransactionManager): DSLContext {
        val config = DefaultConfiguration()
        config.setSQLDialect(SQLDialect.POSTGRES)
        config.setTransactionProvider(SpringTransactionProvider(transactionManager))
        config.set(connectionProvider)
        config.setSettings(Settings().withExecuteWithOptimisticLocking(true).withExecuteWithOptimisticLockingExcludeUnversioned(true))
        return DefaultDSLContext(config)
    }

}