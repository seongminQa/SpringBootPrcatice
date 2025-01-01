package kr.co.znznfltm.myrestfulservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

}
