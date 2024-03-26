package com.example.springSecurity.config;

import com.example.springSecurity.service.MyOAuth2UserService;
import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired private MyOAuth2UserService myOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(auth -> auth.disable())			// 괄호 안에 람다함수를 사용해야 함
                .headers(x -> x.frameOptions(y -> y.disable()))		// CK Editor image upload
                .authorizeHttpRequests(auth -> auth
                        .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()         // 페이지 안에서 리턴으로 보내는 것(FORWARD)들은 전부 권한을 묻지 않겠다라는 뜻
                        .requestMatchers( "/user/register",         //"/user/login"
                                "/img/**", "/css/**", "/js/**", "/error/**").permitAll()            // 해당 경로 또는 아래있는 것들은 권한을 묻지 않겠다라는 뜻
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")            // 해당 경로로 접근할 때 권한이 있는지 (ROLE_ADMIN) 를 확인하고 있으면 보내줌
                        .anyRequest().authenticated()       // 나머지 주소의 것들은 전부 권한을 묻겠다.없을시 로그인창으로
                )
                .formLogin(auth -> auth
                        .loginPage("/user/login")		// 로그인 폼
                        .loginProcessingUrl("/user/login")	// 스프링 시큐리티가 낚아 챔. UserDetailsService 구현객체에서 처리해주어야 함
                        .usernameParameter("uid")
                        .passwordParameter("pwd")
                        .defaultSuccessUrl("/user/loginSuccess", true) 		// 내가 로그인후 해야할 일, 예) 세션 세팅, 오늘의 메세지 등
                        .permitAll()
                )
                .oauth2Login(auth -> auth                   // 소셜 로그인을 담당하는 부분!
                        .loginPage("/user/login")
                        // 소셜 로그인이 완료된 이후의 후처리
                        // 1. 코드 받기(인증), 2. 액세스 토근받기(권한) 3. 사용자 프로필 정보를 가져옴
                        // 4. 3에서 받은 정보를 토대로 DB에 없으면 새로운 가입을 시켜줌
                        // 5. 프로바이더가 제공하는 정보 + 추가 정보 수집 ( 주소, ...)
                        .userInfoEndpoint(user -> user.userService(myOAuth2UserService))
                        .defaultSuccessUrl("/user/loginSuccess", true)
                )
                .logout(auth -> auth
                        .logoutUrl("/user/logout")
                        .invalidateHttpSession(true)        // 로그아웃시 세션 초기화
                        .deleteCookies("JSESSIONID")    // 로그아웃시 쿠키 삭제
                        .logoutSuccessUrl("/user/login")        // 로그아웃시 로그인창으로 보냄
                        );
        ;

        return http.build();

        //위의 코드는 Spring Security를 사용하여 웹 애플리케이션의 보안을 설정하는 Spring 구성 클래스입니다.

        //@Configuration 어노테이션은 이 클래스가 Spring의 구성 클래스임을 나타냅니다. @EnableWebSecurity 어노테이션은 Spring Security를 이용한 웹 보안을 활성화합니다.

        //@Bean 어노테이션은 SecurityFilterChain 객체를 반환하는 메서드를 표시합니다. 이 SecurityFilterChain은 Spring Security의 필터 체인을 구성하는 데 사용됩니다.

        //filterChain() 메서드는 HttpSecurity 객체를 매개변수로 받아 Spring Security의 웹 보안 설정을 구성합니다.

        //csrf(auth -> auth.disable()): CSRF(Cross-Site Request Forgery) 보호 기능을 비활성화합니다.
        //headers(x -> x.frameOptions(y -> y.disable())): X-Frame-Options 헤더를 비활성화하여 CKEditor 이미지 업로드를 가능하게 합니다.
        //authorizeHttpRequests(): HTTP 요청에 대한 권한 설정을 구성합니다.
        //dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll(): FORWARD 방식으로 페이지를 전달할 때는 모든 사용자에게 허용합니다.
        //requestMatchers(): 특정 요청 경로에 대한 권한 설정을 구성합니다.
        //anyRequest().authenticated(): 그 외의 모든 요청은 인증된 사용자만 허용합니다.
        //formLogin(auth -> auth.loginPage("/user/login")...): 사용자 로그인 페이지 및 로그인 처리 URL을 설정합니다.
        //logout(auth -> auth.logoutUrl("/user/logout")...): 사용자 로그아웃 처리를 설정합니다.
        //이 설정을 사용하면 Spring Security를 통해 웹 애플리케이션의 인증 및 권한 부여를 구성할 수 있습니다.
    }
}
