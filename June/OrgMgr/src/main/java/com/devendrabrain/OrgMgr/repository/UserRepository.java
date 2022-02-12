package com.devendrabrain.OrgMgr.repository;

import com.devendrabrain.OrgMgr.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = "select * from User where username=:USERNAME",nativeQuery = true)
    public User findByUsername(@Param("USERNAME") String username);
}
