package com.jjo.h2.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.persistence.PersistenceContext;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = DatasourceH2.BASE_PACKAGES, //
		entityManagerFactoryRef = DatasourceH2.ENTITY_MANAGER, //
		transactionManagerRef = DatasourceH2.TRANSACTION_MANAGER)
public class DatasourceH2 {

	static final String ENTITY_MANAGER = "entityManager";
	static final String TRANSACTION_MANAGER = "transactionManagerDataH2";

	static final String BASE_PACKAGES = "com.jjo.h2.repositories";

	@Primary
	@Bean(name = "dataSource")
	@ConfigurationProperties("spring.datasource")
	public HikariDataSource dataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

	@PersistenceContext(unitName = "h2")
	@Primary
	@Bean(name = ENTITY_MANAGER)
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
		return builder.dataSource(dataSource()).packages("com.jjo.h2.model").persistenceUnit("h2").properties(dsProperties()).build();
	}

	private Map<String, Object> dsProperties() {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.hbm2ddl.auto", "create-drop");
		return properties;
	}

	@Bean
	public AuditorAware<String> auditorAwareRef() {
		return () -> Optional.ofNullable(SecurityContextHolder.getContext())
		  .map(SecurityContext::getAuthentication)
		  .filter(Authentication::isAuthenticated)
		  .map(Authentication::getName);
	}
}
