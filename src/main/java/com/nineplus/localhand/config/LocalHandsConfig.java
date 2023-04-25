package com.nineplus.localhand.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Arrays;
import java.util.Locale;

@Configuration
@EnableScheduling
public class LocalHandsConfig {
	private static final String[] BASE_NAMES = { "classpath:i18n/messages" };

	@Value("${spring.mail.host}")
	private String host;

	@Value("${spring.mail.port}")
	private int port;

	@Value("${spring.mail.username}")
	private String username;

	@Value("${spring.mail.password}")
	private String password;

	@Value("${spring.mail.properties.mail.smtp.auth}")
	private boolean propAuth;

	@Value("${spring.mail.properties.mail.smtp.starttls.enable}")
	private boolean propTlsEnale;

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames(BASE_NAMES);
		messageSource.setCacheSeconds(10); // reload messages every 10 seconds
		messageSource.setUseCodeAsDefaultMessage(false);
		messageSource.setDefaultEncoding("UTF-8");

		return messageSource;
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	};

	@Bean
	public AcceptHeaderLocaleResolver localeResolver() {
		AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
		localeResolver.setSupportedLocales(Arrays.asList(new Locale("en"), new Locale("vi")));
		localeResolver.setDefaultLocale(new Locale("en"));
		return localeResolver;
	}
	@Bean
	public LocaleResolver sessionLocaleResolver() {
		return new AcceptHeaderLocaleResolver();
	}

	@Bean
	public InternalResourceViewResolver defaultViewResolver() {
		return new InternalResourceViewResolver();
	}

//	@Bean
//	public JavaMailSender getJavaMailSender() {
//		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//		mailSender.setHost(host);
//		mailSender.setPort(port);
//
//		mailSender.setUsername(username);
//		mailSender.setPassword(password);
//
//		Properties props = mailSender.getJavaMailProperties();
//		props.put("mail.transport.protocol", "smtp");
//		props.put("mail.smtp.auth", propAuth);
//		props.put("mail.smtp.starttls.enable", propTlsEnale);
//
//		return mailSender;
//	}
}
