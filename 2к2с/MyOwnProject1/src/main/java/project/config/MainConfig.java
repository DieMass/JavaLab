package project.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
@EnableJpaRepositories("project.repositories")
@ComponentScan("project")
@EnableAspectJAutoProxy
public class MainConfig {

	@Autowired
	private Environment environment;

	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(hikariDataSource());
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(hikariDataSource());
		entityManagerFactory.setPackagesToScan("project");
		entityManagerFactory.setJpaVendorAdapter(hibernateJpaVendorAdapter);
		entityManagerFactory.setJpaProperties(additionalProperties());
		return entityManagerFactory;
	}

	private Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
		properties.setProperty("hibernate.show_sql", "true");
		return properties;
	}

//	@Bean
//	public PlatformTransactionManager platformTransactionManager(EntityManagerFactory entityManagerFactory) {
//		JpaTransactionManager transactionManager = new JpaTransactionManager();
//		transactionManager.setEntityManagerFactory(entityManagerFactory);
//		return transactionManager;
//	}

	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

	@Bean
	public DataSource hikariDataSource() {
		return new HikariDataSource(hikariConfig());
	}

	@Bean
	public HikariConfig hikariConfig() {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(environment.getProperty("db.url"));
		config.setUsername(environment.getProperty("db.user"));
		config.setPassword(environment.getProperty("db.password"));
		config.setDriverClassName(environment.getProperty("db.driver"));
		return config;
	}

	@Bean
	public ExecutorService executorService() {
		return Executors.newFixedThreadPool(5);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername("Usannarooroc@gmail.com");
		mailSender.setPassword("Euzjkwko8X");
		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");
		return mailSender;
	}

	@Bean
	public FreeMarkerConfigurer getConf() {
		final FreeMarkerConfigurer result = new FreeMarkerConfigurer();
		result.setTemplateLoaderPath("/WEB-INF/ftl/");
		result.setDefaultEncoding("UTF-8");
		return result;
	}

	@Bean
	public freemarker.template.Configuration configuration(FreeMarkerConfigurer freeMarkerConfigurer) {
		freemarker.template.Configuration configuration = freeMarkerConfigurer.getConfiguration();
		configuration.setEncoding(new Locale("ru"), "utf-8");
		return configuration;
	}

	@Bean
	public FreeMarkerViewResolver freemarkerViewResolver() {
		FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
		resolver.setPrefix("");
		resolver.setContentType("text/html; charset=utf-8");
		resolver.setSuffix(".ftl");
		return resolver;
	}
}