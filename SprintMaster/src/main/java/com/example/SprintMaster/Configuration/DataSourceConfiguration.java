package com.example.SprintMaster.Configuration;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.web.client.RestTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfiguration {
	@Value("${spring.property_analytics.datasource.driver-class-name}")
	private String driverClassName;

	@Value("${spring.property_analytics.datasource.url}")
	private String url;

	@Value("${spring.property_analytics.datasource.username}")
	private String username;

	@Value("${spring.property_analytics.datasource.password}")
	private String password;
	
	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(driverClassName);
		hikariConfig.setJdbcUrl(url);
		hikariConfig.setUsername(username);
		hikariConfig.setPassword(password);

		hikariConfig.setMaximumPoolSize(200);
		hikariConfig.setConnectionTestQuery("SELECT 1");
		hikariConfig.setPoolName("springHikariCP");

		hikariConfig.setConnectionTimeout(180000);
		hikariConfig.setMinimumIdle(5);

		HikariDataSource dataSource = new HikariDataSource(hikariConfig);

		return dataSource;
	}

	@Bean(name = {"sessionFactory","entityManagerFactory"})
	public SessionFactory getSessionFactory(DataSource dataSource) {
		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
		sessionBuilder.scanPackages("com.example.SprintMaster","com.example.*");
		return sessionBuilder.buildSessionFactory();
	}
	
	@Bean
    public RestTemplate HttpsRestClient(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

}
