package com.devendrabrain.OrgMgr.dao.impl;

import com.devendrabrain.OrgMgr.dao.StudentDAO;
import com.devendrabrain.OrgMgr.entity.Student;
import com.devendrabrain.OrgMgr.repository.StudentRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentDAOImpl implements StudentDAO {

    @Autowired
    StudentRepositoy studentRepositoy;

    @Override
    public List<Student> fetchAllStudent() {
        return studentRepositoy.findAll();
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepositoy.save(student);
    }

    @Override
    public void delete(Student student) {
        studentRepositoy.delete(student);
    }

    @Override
    public Student findById(Long id) {
        return studentRepositoy.findById(id).orElse(null);
    }
}
