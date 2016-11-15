/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.test;

import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.application.config.WebAppInitializer;
import com.mongodb.MongoClient;

/**
 *
 * @author omm
 */
@Configuration
@PropertySource("file:fastticket/properties/FastticketWebAppConfig.properties")
@ComponentScan("com.sujavtech")
@EnableJpaRepositories(basePackages = { "com.sujavtech" })
@EnableTransactionManagement
@EnableScheduling
@EnableMongoRepositories(basePackages = { "com.sujavtech" })
public class AppConfigLocal {

	@Autowired
	private Environment env;

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		String driver = env.getProperty("DB_DRIVER");
		String url = env.getProperty("DB_URL");
		String user = env.getProperty("DB_USER");
		String password = env.getProperty("DB_PASSWORD");
		String autoCommit = env.getProperty("DB_DEFAULT_AUTOCOMMIT");
		String maxConn = env.getProperty("DB_MAX_CONNECTIONS");
		String minConn = env.getProperty("DB_INITIAL_CONNECTIONS");
		String connecionTimeout = env.getProperty("DB_CONNECTION_TIMEOUT");
		String maxIdleConnetions = env.getProperty("DB_MAX_IDLE_CONNECTIONS");

		String poolPreparedStmts = env
				.getProperty("DB_POOL_PREPAREDSTATEMENTS");
		String maxPoolPreparedStatements = env
				.getProperty("DB_MAX_OPEN_PREPAREDSTATEMENTS_PER_CONNECTION");

		System.out.println("**********dataSource url: " + url);
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(password);
		dataSource.setDefaultAutoCommit(Boolean.parseBoolean(autoCommit));
		dataSource.setInitialSize(Integer.parseInt(minConn));
		dataSource.setMaxActive(Integer.parseInt(maxConn));
		dataSource.setMaxWait(Long.parseLong(connecionTimeout));
		dataSource.setMaxIdle(Integer.parseInt(maxIdleConnetions));
		dataSource.setPoolPreparedStatements(Boolean
				.parseBoolean(poolPreparedStmts));
		dataSource.setMaxOpenPreparedStatements(Integer
				.parseInt(maxPoolPreparedStatements));
		dataSource.setRemoveAbandoned(true);
		dataSource.setTestOnBorrow(true);
		dataSource.setTestWhileIdle(true);
		dataSource.setValidationQuery("select 1");
		dataSource.setValidationQueryTimeout(60);

		return dataSource;
	}

	@Bean
	public ApplicationContext applicationContext() {
		System.out.println("**********applicationContext");
		return WebAppInitializer.getApplicationContext();
	}

	@Bean(name = "entityManagerFactory", destroyMethod = "destroy")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(
			DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setPackagesToScan("com.sujavtech");
		emf.setPersistenceProvider(new HibernatePersistenceProvider());
		Properties jpaProperties = getHibernateProperties();
		emf.setJpaProperties(jpaProperties);
		emf.setDataSource(dataSource);
		System.out.println("**********entityManagerFactory " + emf);
		return emf;
	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		System.out
				.println("**********transactionManager " + transactionManager);
		return transactionManager;
	}

	private Properties getHibernateProperties() {
		Properties prop = new Properties();
		prop.put("hibernate.hbm2ddl.auto",
				env.getProperty("hibernate.hbm2ddl.auto"));
		prop.put("hibernate.format_sql",
				env.getProperty("hibernate.format_sql"));
		prop.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		prop.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
		return prop;
	}

	@Bean(destroyMethod = "shutdown")
	public Executor taskExecutor() {
		System.out.println("*********Init thread pool for schedulers");
		return Executors.newScheduledThreadPool(100);
	}

	@Bean(name = "mongoTemplate")
	public MongoTemplate mongoTemplate() {
		MongoClient mongoClient = new MongoClient(
				this.env.getProperty("mongodb_service_host"),
				Integer.parseInt(this.env.getProperty("mongodb_service_port")));
		MongoTemplate mongoTemplate = new MongoTemplate(mongoClient,
				this.env.getProperty("mongodb_database"));
		return mongoTemplate;
	}

}
