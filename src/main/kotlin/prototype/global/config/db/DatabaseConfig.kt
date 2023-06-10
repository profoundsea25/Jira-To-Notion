package prototype.global.config.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import jakarta.persistence.EntityManagerFactory
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy
import org.hibernate.cfg.AvailableSettings.*
import org.hibernate.dialect.H2Dialect
import org.hibernate.tool.schema.Action
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

@Configuration
@ConfigurationProperties(prefix = DbConfigConst.configPrefix)
@EnableJpaRepositories(basePackages = [DbConfigConst.repositoryBasePackage])
class DatabaseConfig : HikariConfig() {

    @Bean
    fun dataSource(): DataSource =
        LazyConnectionDataSourceProxy(HikariDataSource(this))

    @Bean
    fun entityManagerFactory(
        dataSource: DataSource
    ): EntityManagerFactory {
        val em = LocalContainerEntityManagerFactoryBean()
        em.dataSource = dataSource
        em.jpaVendorAdapter = HibernateJpaVendorAdapter()
        em.setPackagesToScan(DbConfigConst.entityBasePackage)
        em.setJpaPropertyMap(
            mapOf(
                HBM2DDL_AUTO to Action.CREATE.externalHbm2ddlName,
                DIALECT to H2Dialect::class.java,
                PHYSICAL_NAMING_STRATEGY to CamelCaseToUnderscoresNamingStrategy::class.java,
                LOG_SLOW_QUERY to 60 * 1000L, // ms
                SHOW_SQL to true,
                FORMAT_SQL to true,
                HIGHLIGHT_SQL to true,
            )
        )
        em.afterPropertiesSet()
        return em.`object`!!
    }

    @Bean
    fun transactionManager(
        entityManagerFactory: EntityManagerFactory
    ): PlatformTransactionManager = JpaTransactionManager()
        .also { it.entityManagerFactory = entityManagerFactory }

}