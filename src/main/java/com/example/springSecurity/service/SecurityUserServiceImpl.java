package com.example.springSecurity.service;

import com.example.springSecurity.Dao.SecurityUserDao;
import com.example.springSecurity.entity.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityUserServiceImpl implements SecurityUserService {
//    private SecurityUserDao securityDao;            
//    @Autowired
//    public SecurityUserServiceImpl(SecurityUserDao securityDao){
//        this.securityDao = securityDao;             //생성자 타입은 이렇게 하는걸 권장하지만(디펜더시 주입)
//    }
    private final SecurityUserDao securityDao;          // 이처럼 @RequiredArgsConstructor 를 선언하고 난후 이렇게 사용가능

    @Override
    public SecurityUser getUserByUid(String uid) {
        return securityDao.getUserByUid(uid);
    }

    @Override
    public List<SecurityUser> getSecurityUserList(int page) {
       int count = COUNT_PER_PAGE;
       int offset = (page - 1) * COUNT_PER_PAGE;
        return securityDao.getSecurityUserList(count, offset);
    }

    @Override
    public int getSecurityUserCount() {
        return securityDao.getSecurityUserCount();
    }

    @Override
    public void insertSecurityUser(SecurityUser securityUser) {
        securityDao.insertSecurityUser(securityUser);
    }

    @Override
    public void updateSecurityUser(SecurityUser securityUser) {
        securityDao.updateSecurityUser(securityUser);
    }

    @Override
    public void deleteSecurityUser(String uid) {
        securityDao.deleteSecurityUser(uid);
    }
}
