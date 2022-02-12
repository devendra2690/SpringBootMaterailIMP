package com.devendrabrain.OrgMgr.dao;

import com.devendrabrain.OrgMgr.entity.Student;

import java.util.List;

public interface StudentDAO {

    public List<Student> fetchAllStudent();

    public Student saveStudent(Student student);

    public void delete(Student student);

    public Student findById(Long id);
}
