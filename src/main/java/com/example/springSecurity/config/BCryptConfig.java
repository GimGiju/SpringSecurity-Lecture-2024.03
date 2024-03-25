package com.example.springSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BCryptConfig {

    @Bean
    public BCryptPasswordEncoder bCryptEncoder() {
        return new BCryptPasswordEncoder();
    }

    //위의 코드는 Spring Security를 사용하여 비밀번호를 안전하게 저장하고 검증하기 위해 BCrypt 암호화를 구현하는 방법을 설정하는 Spring 구성 클래스입니다.

    //@Configuration 어노테이션은 이 클래스가 Spring의 구성 클래스임을 나타냅니다. @Bean 어노테이션은 스프링 컨테이너에 빈을 등록하는 메서드를 표시합니다.

    //BCryptPasswordEncoder는 Spring Security에서 제공하는 비밀번호 암호화 도구 중 하나입니다. 이 클래스는 비밀번호를 안전하게 암호화하고 검증할 수 있습니다.

    //bCryptEncoder() 메서드는 BCryptPasswordEncoder의 새 인스턴스를 생성하여 Spring 컨테이너에 빈으로 등록합니다.
    // 이렇게 하면 다른 Spring 빈에서 이 BCryptPasswordEncoder를 주입받아 비밀번호를 암호화하거나 검증할 수 있습니다.

    //이 설정을 사용하면 Spring Security에서 암호화된 비밀번호를 저장하고 인증하는 데 사용할 수 있습니다.

}