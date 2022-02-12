package com.devendrabrain.StudentMgr.repository;

import com.devendrabrain.StudentMgr.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query("select * from User where username=:USERNAME")
    public User findByUsername(@Param("USERNAME") String username);
}
