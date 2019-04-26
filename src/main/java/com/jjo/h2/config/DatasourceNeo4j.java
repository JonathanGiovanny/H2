package com.jjo.h2.config;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;

@Configuration
@EnableNeo4jRepositories( //
    sessionFactoryRef = DatasourceNeo4j.SESSION_FACTORY, //
    basePackages = DatasourceNeo4j.BASE_PACKAGES, //
    transactionManagerRef = DatasourceNeo4j.TRANSACTION_MANAGER)
public class DatasourceNeo4j {

  static final String SESSION_FACTORY = "sessionFactoryDataNeo4j";
  public static final String TRANSACTION_MANAGER = "transactionManagerDataNeo4j";

  static final String BASE_PACKAGES = "com.jjo.h2.repositories.security";
  static final String BASE_PACKAGES_DOMAIN = "com.jjo.h2.model.security";

  @Bean
  @ConfigurationProperties("spring.data.neo4j")
  public Neo4jProperties neo4jProperties() {
    return new Neo4jProperties();
  }

  @Bean
  public org.neo4j.ogm.config.Configuration configurationNeo4j() {
    return neo4jProperties().createConfiguration();
  }

  @Bean(name = SESSION_FACTORY)
  public SessionFactory graphSessionFactory() {
    return new SessionFactory(configurationNeo4j(), BASE_PACKAGES_DOMAIN);
  }

  @Bean(name = TRANSACTION_MANAGER)
  public Neo4jTransactionManager graphTransactionManager(
      @Qualifier(SESSION_FACTORY) SessionFactory sessionFactory) {
    return new Neo4jTransactionManager(sessionFactory);
  }
}
