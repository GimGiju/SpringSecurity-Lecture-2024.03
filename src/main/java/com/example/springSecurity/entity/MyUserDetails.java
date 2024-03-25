package com.example.springSecurity.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// Spring Security가 로그인 POST 요청을 낚아채서 로그인을 진행시킴
// 로컬 로그인 - UserDetails 구현
// 소셜 로그인 - OAuth2User 구현
@RequiredArgsConstructor
public class MyUserDetails implements UserDetails {
    private final SecurityUser securityUser;
    
    // 사용자의 권한을 리턴,  관리자인지 사용자인지를 리턴해줌
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return securityUser.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return securityUser.getPwd();
    }

    @Override
    public String getUsername() {           // 가지고 있는 아이디가 와야함
        return securityUser.getUid();
    }

    @Override
    public boolean isAccountNonExpired() {
        if (securityUser.getIsDeleted() == 0){
            return true;
        }
        return false;

    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
