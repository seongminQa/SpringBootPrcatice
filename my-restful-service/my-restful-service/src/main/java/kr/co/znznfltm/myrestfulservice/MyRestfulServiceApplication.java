package kr.co.znznfltm.myrestfulservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@SpringBootApplication
public class MyRestfulServiceApplication {

	public static void main(String[] args) {

//		ApplicationContext ac = SpringApplication.run(MyRestfulServiceApplication.class, args);
//		String[] allBeanNames = ac.getBeanDefinitionNames();
//
//		// 초기 상태에 등록된 Bean의 이름을 모두 출력해보자.
//		for(String beanName : allBeanNames) {
//			System.out.println(beanName);
//		}

		SpringApplication.run(MyRestfulServiceApplication.class, args);
	}

	@Bean // Spring Context가 처음 기동될 때 메모리에 Bean으로 등록됨.
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);
		return localeResolver;
	}

}
