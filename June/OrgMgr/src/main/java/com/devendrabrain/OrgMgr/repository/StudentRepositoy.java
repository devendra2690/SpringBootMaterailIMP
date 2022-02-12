package com.devendrabrain.OrgMgr.repository;

import com.devendrabrain.OrgMgr.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepositoy extends JpaRepository<Student,Long> {
}
