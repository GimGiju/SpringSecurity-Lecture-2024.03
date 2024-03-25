package com.example.springSecurity.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.springSecurity.entity.SecurityUser;


//이 DAO 인터페이스를 통해 Spring Security 애플리케이션에서 사용자 관련 데이터베이스 작업을 수행할 수 있습니다.
@Mapper             //@Mapper 어노테이션은 이 인터페이스가 MyBatis 매퍼임을 나타냅니다.
public interface SecurityUserDao {

    // @Select: SELECT 쿼리를 실행하기 위한 어노테이션입니다.
    @Select("select * from securityUser where uid=#{uid}")
    SecurityUser getUserByUid(String uid);
    //getUserByUid(String uid): UID를 기반으로 사용자를 조회하는 메서드입니다.

    @Select("select * from securityUser where isDeleted=0 order by regDate desc"
            + " limit #{count} offset #{offset}")
    List<SecurityUser> getSecurityUserList(int count, int offset);
    //getSecurityUserList(int count, int offset): 페이징된 사용자 목록을 조회하는 메서드입니다.

    @Select("select count(uid) from securityUser where isDeleted=0")
    int getSecurityUserCount();     //getSecurityUserCount(): 사용자 수를 조회하는 메서드입니다.

    //@Insert: INSERT 쿼리를 실행하기 위한 어노테이션으로, 사용자를 삽입하는 메서드입니다.
    @Insert("insert into securityUser values (#{uid}, #{pwd}, #{uname}, #{email},"
            + " default, default, #{picture}, #{provider}, default)")
    void insertSecurityUser(SecurityUser securityUser);


    // @Update: UPDATE 쿼리를 실행하기 위한 어노테이션으로, 사용자 정보를 업데이트하거나 삭제하는 메서드입니다.
    @Update("update securityUser set pwd=#{pwd}, uname=#{uname}, email=#{email},"
            + " picture=#{picture} where uid=#{uid}")
    void updateSecurityUser(SecurityUser securityUser);
    //updateSecurityUser(SecurityUser securityUser): 사용자 정보를 업데이트하는 메서드입니다.

    @Update("update securityUser set isDeleted=1 where uid=#{uid}")
    void deleteSecurityUser(String uid);
    //deleteSecurityUser(String uid): 사용자를 삭제하는 메서드입니다.

}