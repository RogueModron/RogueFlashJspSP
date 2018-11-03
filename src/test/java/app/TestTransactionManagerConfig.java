package app;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.BootstrapMode;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Import({
	TestDefaultDataSourceConfig.class
})
@EnableJpaRepositories(
	basePackages = "app.repositories",
	bootstrapMode = BootstrapMode.DEFERRED
)
@EnableTransactionManagement
public class TestTransactionManagerConfig
{
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource)
	{
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(dataSource);
		factory.setPackagesToScan("app.model");

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setShowSql(true);

		factory.setJpaVendorAdapter(vendorAdapter);

		Properties jpaProperties = new Properties();
		jpaProperties.setProperty("hibernate.cache.use_second_level_cache", "false");
		jpaProperties.setProperty("hibernate.flushMode", "FLUSH_MANUAL");
		jpaProperties.setProperty("hibernate.format_sql", "true");
		jpaProperties.setProperty("hibernate.globally_quoted_identifiers", "true");
		jpaProperties.setProperty("hibernate.hbm2ddl.auto", "none");
		jpaProperties.setProperty("hibernate.jdbc.log.warnings", "true");
		jpaProperties.setProperty("hibernate.query.jpaql_strict_compliance", "true");
		//jpaProperties.setProperty("hibernate.show_sql", "true");
		
		factory.setJpaProperties(jpaProperties);
		
		return factory;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory)
	{
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory);
		return txManager;
	}
}
