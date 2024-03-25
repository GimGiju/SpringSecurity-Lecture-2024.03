package com.example.springSecurity.service;


import com.example.springSecurity.entity.SecurityUser;

import java.util.List;

public interface SecurityUserService {
    public static final int COUNT_PER_PAGE = 10;

    SecurityUser getUserByUid(String uid);

    List<SecurityUser> getSecurityUserList(int page);

    int getSecurityUserCount();

    void insertSecurityUser(SecurityUser securityUser);

    void updateSecurityUser(SecurityUser securityUser);

    void deleteSecurityUser(String uid);


}
