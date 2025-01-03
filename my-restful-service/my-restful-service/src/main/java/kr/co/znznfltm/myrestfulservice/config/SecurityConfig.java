package kr.co.znznfltm.myrestfulservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {
    /*
        사용자 데이터가 메모리 상에서 생성이 되면서 생성된 메모리 객체를 바로 쓸 수 있게끔 실습하려고 한다.
    */
    @Bean
    UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

        UserDetails newUser = User.withUsername("znznfltm")
                .password(passwordEncoder().encode("seongmin"))
                .authorities("read")
                .build();

        userDetailsManager.createUser(newUser);
        return userDetailsManager;
    }

    // password 인코딩 과정.
    // 인코딩 할 수 있는 알고리즘을 사용해야 한다!! 외부 노출 우려
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
