package com.devendrabrain.OrgMgr.service;

import com.devendrabrain.OrgMgr.bo.StudentBO;

import java.util.List;

public interface StudentService {

    public List<StudentBO> fetchAllStudent();

    public StudentBO saveStudent(StudentBO studentBO);

    public void deleteStudent(Long id);

    public StudentBO fetchById(Long id);
}
