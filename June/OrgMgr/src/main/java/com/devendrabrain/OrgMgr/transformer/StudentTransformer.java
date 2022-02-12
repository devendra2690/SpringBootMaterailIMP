package com.devendrabrain.OrgMgr.transformer;

import com.devendrabrain.OrgMgr.bo.StudentBO;
import com.devendrabrain.OrgMgr.dto.StudentDTO;
import com.devendrabrain.OrgMgr.entity.Student;

public class StudentTransformer {

    public static StudentBO transformStudentToStudentBO(Student student) {

        StudentBO studentBO = StudentBO.builder().id(student.getId()).city(student.getCity()).classroom(student.getClassroom()).name(student.getName()).build();
        return studentBO;
    }
}
