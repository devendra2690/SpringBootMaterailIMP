package com.devendrabrain.OrgMgr.service.impl;

import com.devendrabrain.OrgMgr.bo.StudentBO;
import com.devendrabrain.OrgMgr.constant.ErrorCategory;
import com.devendrabrain.OrgMgr.dao.StudentDAO;
import com.devendrabrain.OrgMgr.dto.StudentDTO;
import com.devendrabrain.OrgMgr.entity.Student;
import com.devendrabrain.OrgMgr.service.StudentService;
import com.devendrabrain.OrgMgr.utils.ValidationUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private static final String CLASS_NAME = StudentServiceImpl.class.getName();

    @Autowired
    StudentDAO studentDAO;

    @Override
    public List<StudentBO> fetchAllStudent() {

        List<Student> studentList = studentDAO.fetchAllStudent();

        // Transform from Student Entity to StudentDTO
        List<StudentBO> studenBoList = studentList.stream().map(student -> {
            StudentBO studentBO = StudentBO.builder().id(student.getId()).city(student.getCity()).classroom(student.getClassroom()).name(student.getName()).build();
            return  studentBO;
         }).collect(Collectors.toList());


        return studenBoList;
    }

    @Override
    public StudentBO saveStudent(StudentBO inputStudentBO) {

        // Transform StudentBO to student
        Student studentToSave = new Student();
        if(StringUtils.isNotBlank(inputStudentBO.getCity())) {
            studentToSave.setCity(inputStudentBO.getCity());
        }
        if(StringUtils.isNotBlank(inputStudentBO.getClassroom())) {
            studentToSave.setClassroom(inputStudentBO.getClassroom());
        }
        if(StringUtils.isNotBlank(inputStudentBO.getName())) {
            studentToSave.setName(inputStudentBO.getName());
        }

        // save Student Records
        Student studentEntiry = studentDAO.saveStudent(studentToSave);

        // Transform from Student to StudentBO
       Optional<StudentBO> studentBO = Arrays.asList(studentEntiry).stream().map(student -> {
            StudentBO studentObjBO = StudentBO.builder().id(student.getId()).city(student.getCity()).classroom(student.getClassroom()).name(student.getName()).build();
            return  studentObjBO;
        }).collect(Collectors.toList()).stream().findFirst();

       return studentBO.orElse(null);
    }

    @Override
    public void deleteStudent(Long id) {

        // Find Student Record
        Student student = studentDAO.findById(id);
        if(student == null) {
            ValidationUtils.getValidationError(ErrorCategory.ER0004, CLASS_NAME);
        }

        studentDAO.delete(student);
    }

    @Override
    public StudentBO fetchById(Long id) {

        // Fetch Student record
        Student studentEntiry = studentDAO.findById(id);
        if(studentEntiry == null) {
            ValidationUtils.getValidationError(ErrorCategory.ER0004, CLASS_NAME);
        }

        // Transform from Student to StudentBO
        Optional<StudentBO> studentBO = Arrays.asList(studentEntiry).stream().map(student -> {
            StudentBO studentObjBO = StudentBO.builder().id(student.getId()).city(student.getCity()).classroom(student.getClassroom()).name(student.getName()).build();
            return  studentObjBO;
        }).collect(Collectors.toList()).stream().findFirst();


        return studentBO.orElse(null);
    }
}
