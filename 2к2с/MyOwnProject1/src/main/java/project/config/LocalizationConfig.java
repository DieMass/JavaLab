package project.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan("project")
public class LocalizationConfig implements WebMvcConfigurer {

	// добавляем перехватчик в контекст сервлетов
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

	// Перехватчик запросов - все запросы на сервер будут проходить через него
	// если в запросе есть параметр ?lang, то он изменит локализацию
	// get localhost/profile?lang=ru
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}

	// этот бин определяет текущую локализацию исходя из кук
	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
		cookieLocaleResolver.setCookieName("localeInfo");
		cookieLocaleResolver.setCookieMaxAge(60 * 60 * 24 * 365);
		return cookieLocaleResolver;
	}

	// говорим, где находятся наши файлы с локализацией
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages/messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	// ru -> messages_ru
	// en -> messages_en
	@Override
	public MessageCodesResolver getMessageCodesResolver() {
		DefaultMessageCodesResolver codesResolver = new DefaultMessageCodesResolver();
		codesResolver.setMessageCodeFormatter(DefaultMessageCodesResolver.Format.POSTFIX_ERROR_CODE);
		return codesResolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/js/**").addResourceLocations("/static/js/");
		registry.addResourceHandler("/css/**").addResourceLocations("/static/css/");
	}
}

