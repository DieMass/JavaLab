package project.server;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class ApplicationContextConfig {

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
//		properties.setProperty("hibernate.show_sql", "true");
		return properties;
	}

	@Bean
	public PlatformTransactionManager platformTransactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);

		return transactionManager;
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
	public ExecutorService executorService() {
		return Executors.newFixedThreadPool(5);
	}

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("WEB-INF/ftl/");
		viewResolver.setSuffix(".ftl");
		return viewResolver;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DataSource hikariDataSource() {
		return new HikariDataSource(hikariConfig());
	}

	@Bean
	public freemarker.template.Configuration configuration() {
		try {
			freemarker.template.Configuration cfg =
					new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_29);
			String s = File.separator;
			cfg.setDirectoryForTemplateLoading(
			new File((System.getProperty("os.name").contains("Win") ?
							"D:\\" : "/mnt/3E66C61266C5CB3B/")
							+"Projects"+s+"Java"+s+"Education"+s+"JavaLab"+s+"2к2с"+s+"MyOwnProject1"+s+"src"+s+"main"+s+"webapp"+s+"WEB-INF"+s+"ftl"));
			cfg.setDefaultEncoding("UTF-8");
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			return cfg;
		} catch (IOException e) {
			throw new IllegalStateException();
		}
	}
}