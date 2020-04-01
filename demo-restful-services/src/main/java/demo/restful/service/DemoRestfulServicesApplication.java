package demo.restful.service;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@SpringBootApplication
public class DemoRestfulServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoRestfulServicesApplication.class, args);
	}
	@Bean
	public LocaleResolver  localeResolver() {
		AcceptHeaderLocaleResolver locale=new AcceptHeaderLocaleResolver();
		//SessionLocaleResolver locale = new SessionLocaleResolver();
		locale.setDefaultLocale(Locale.US);
		return locale;
	}
	
	/*@Bean This can be removed as we have the property in application.properties
	public ResourceBundleMessageSource resourceBundleMessageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}*/

}
