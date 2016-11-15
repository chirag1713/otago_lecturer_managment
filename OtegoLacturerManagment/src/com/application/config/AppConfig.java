package com.application.config;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
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
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.emarket.cacheprovider.CacheProvider;
import com.emarket.emarketcommon.pojo.Constants;
import com.emarket.interceptor.AllUrlInterceptor;
import com.emarket.utill.EmarketUtill;
import com.mongodb.MongoClient;

/**
 *
 * @author Prashant
 */
@Configuration
@EnableWebMvc
@PropertySource("file:emarket/properties/E-MarketAppConfig.properties")
@ComponentScan("com.emarket")
@EnableJpaRepositories(basePackages = { "com.emarket" })
@EnableTransactionManagement
@EnableMongoRepositories(basePackages = { "com.emarket" })
public class AppConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private Environment env;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String property = env.getProperty("STATIC_WEB_RESORCES_FOLDERS");
        System.out.println("**********addResourceHandlers property: " + property);
        if (null != property && !property.isEmpty()) {
            String[] arr = property.split(",");
            for (String string : arr) {
                registry.addResourceHandler(string + "**").addResourceLocations(string);
            }
        }
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        System.out.println("**********configureDefaultServletHandling");
        configurer.enable();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        AllUrlInterceptor allUrlInterceptor = WebAppInitializer.getApplicationContext().getBean(AllUrlInterceptor.class);
        //adding locale change interceptor
        System.out.println("**********addInterceptors adding locale change interceptor");
//        registry.addInterceptor(localChangeInterceptor());
        registry.addInterceptor(allUrlInterceptor);
    }

    @Bean
    public ViewResolver configureViewResolver() {
        String property = env.getProperty("VIEWS_JSP_FOLDER");
        System.out.println("**********configureViewResolver property: " + property);
        InternalResourceViewResolver viewResolve = new InternalResourceViewResolver();
        viewResolve.setPrefix(property);
        viewResolve.setSuffix(".jsp");
        return viewResolve;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("utf-8");
        return resolver;
    }

    // @Bean
    // public ReloadableResourceBundleMessageSource messageSource() {
    // ReloadableResourceBundleMessageSource messageSource = new
    // ReloadableResourceBundleMessageSource();
    // String messageFolder = env.getProperty("MESSAGE_FOLDER");
    // String messageFilePrefix = env.getProperty("MESSAGE_FILE_PREFIX");
    // String charEncoading = env.getProperty("CHAR_ENCOADING");
    // System.out.println("**********messageSource messageFolder: " +
    // messageFolder
    // + ", messageFilePrefix: " + messageFilePrefix + ", charEncoading: " +
    // charEncoading);
    // if (null == messageFolder || messageFolder.isEmpty()) {
    // messageFolder = "/resources/";
    // }
    // if (null == messageFilePrefix || messageFilePrefix.isEmpty()) {
    // messageFilePrefix = "messages";
    // }
    // if (null == charEncoading || charEncoading.isEmpty()) {
    // charEncoading = "UTF-8";
    // }
    // messageSource.setBasename(messageFolder + messageFilePrefix);
    // messageSource.setDefaultEncoding(charEncoading);
    // return messageSource;
    // }
    // /**
    // * ByDefault dispatcher servlet looks for the bean with id
    // [localeResolver]
    // * in the context, hence @Bean (name = "localeResolver")
    // *
    // * @return
    // */
    // @Bean(name = "localeResolver")
    // public LocaleResolver localeResolver() {
    // CookieLocaleResolver localeResolver = new CookieLocaleResolver();
    //
    // String language = env.getProperty("LOCALE_LANGUAGE");
    // String country = env.getProperty("LOCALE_COUNTRY");
    // String cookieName = env.getProperty("LOCALE_COOKIE_NAME");
    // String cookieMaxAge = env.getProperty("LOCALE_COOKIE_MAX_AGE_SECONDS");
    //
    // System.out.println("**********localResolver before language: " + language
    // + ", country: " + country + ", cookieName: " + cookieName +
    // ", cookieMaxAge: " + cookieMaxAge);
    //
    // if (null == language || language.isEmpty()) {
    // language = "en";
    // country = "US";
    // } else {
    // if (null == country || country.isEmpty()) {
    // country = null;
    // }
    // }
    //
    // if (null == cookieName || cookieName.isEmpty()) {
    // cookieName = "sujavtechlocal";
    // }
    // if (null == cookieMaxAge || cookieMaxAge.isEmpty()) {
    // cookieMaxAge = "-1";
    // }
    //
    // System.out.println("**********localResolver after language: " + language
    // + ", country: " + country + ", cookieName: " + cookieName +
    // ", cookieMaxAge: " + cookieMaxAge);
    //
    // Locale locale = new Locale(language, country);
    // localeResolver.setDefaultLocale(locale);
    // localeResolver.setCookieName(cookieName);
    // localeResolver.setCookieMaxAge(Integer.parseInt(cookieMaxAge));
    //
    // return localeResolver;
    // }
    //
    // @Bean
    // public LocaleChangeInterceptor localChangeInterceptor() {
    // String localeParam = env.getProperty("LOCALE_REQUEST_PARAM");
    // System.out.println("**********localChangeInterceptor localeParam: " +
    // localeParam);
    // if (null == localeParam || localeParam.isEmpty()) {
    // localeParam = "locale";
    // }
    // LocaleChangeInterceptor changeInterceptor = new
    // LocaleChangeInterceptor();
    // changeInterceptor.setParamName(localeParam);
    // return changeInterceptor;
    // }
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
        String poolPreparedStmts = env.getProperty("DB_POOL_PREPAREDSTATEMENTS");
        String maxPoolPreparedStatements = env.getProperty("DB_MAX_OPEN_PREPAREDSTATEMENTS_PER_CONNECTION");

        System.out.println("**********dataSource Url " + url);
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
        dataSource.setPoolPreparedStatements(Boolean.parseBoolean(poolPreparedStmts));
        dataSource.setMaxOpenPreparedStatements(Integer.parseInt(maxPoolPreparedStatements));
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
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPackagesToScan("com.emarket");
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
        System.out.println("**********transactionManager " + transactionManager);
        return transactionManager;
    }

    private Properties getHibernateProperties() {
        Properties prop = new Properties();
        prop.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        prop.put("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
        prop.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        prop.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        return prop;
    }

    @Bean
    public CacheProvider cacheProvider() {
        System.out.println("*********Init cacheProvider");
        String ip = this.env.getProperty("CACHE_SERVER_IP");
        int port = Integer.parseInt(this.env.getProperty("CACHE_SERVER_PORT"));
        int minCacheServerCon = Integer.parseInt(this.env.getProperty("CACHE_SERVER_MIN_CON"));
        int maxCacheServerCon = Integer.parseInt(this.env.getProperty("CACHE_SERVER_MAX_CON"));
        int serverConTimeout = Integer.parseInt(this.env.getProperty("CACHE_SERVER_CON_TIMEOUT"));

        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setBlockWhenExhausted(true);
        config.setMaxIdle(minCacheServerCon);
        config.setMaxTotal(maxCacheServerCon);
        config.setMaxWaitMillis(serverConTimeout);
        return new CacheProvider(config, ip, port, minCacheServerCon, maxCacheServerCon, serverConTimeout, Constants.CACHE_KEY_PREFIX_WEB);
    }

    @Bean(name = "mongoTemplate")
    public MongoTemplate mongoTemplate() {
        MongoClient mongoClient = new MongoClient(this.env.getProperty("mongodb_service_host"), Integer.parseInt(this.env.getProperty("mongodb_service_port")));
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, this.env.getProperty("mongodb_database"));
        return mongoTemplate;
    }

    @Bean
    public EmarketUtill commonUtil() {
        return new EmarketUtill();
    }
}
