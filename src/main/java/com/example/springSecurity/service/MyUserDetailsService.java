package com.example.springSecurity.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springSecurity.entity.MyUserDetails;
import com.example.springSecurity.entity.SecurityUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// Spring Security에서 사용자 인증을 위해 UserDetailsService를 구현한 서비스 클래스입니다.
//이 서비스 클래스를 통해 Spring Security에서 사용자 인증을 처리할 수 있습니다. 사용자가 로그인할 때,
// Spring Security는 loadUserByUsername 메서드를 호출하여 사용자를 조회하고 인증에 사용합니다.
@Service  //@Service 어노테이션은 이 클래스가 서비스 빈임을 나타냅니다.
@Slf4j      //@Slf4j 어노테이션은 로깅을 위해 Lombok에서 제공하는 어노테이션입니다.
@RequiredArgsConstructor  //@RequiredArgsConstructor 어노테이션은 Lombok을 사용하여 생성자 주입을 자동으로 생성합니다.
public class MyUserDetailsService implements UserDetailsService {
    private final SecurityUserService securityService;

    @Override
    //loadUserByUsername(String username): 주어진 사용자 이름(여기서는 UID)을 사용하여 사용자 정보를 조회하는 메서드입니다.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityUser securityUser = securityService.getUserByUid(username);
        //securityService.getUserByUid(username): 주어진 UID를 사용하여 사용자 정보를 데이터베이스에서 조회합니다.

        if (securityUser != null) {
            log.info("Login 완료: " + securityUser.getUid());
            return new MyUserDetails(securityUser);
            //MyUserDetails(securityUser): 조회된 사용자 정보를 기반으로 UserDetails 인터페이스를 구현한 MyUserDetails 객체를 생성하여 반환합니다.
        }
        return null;
    }

}