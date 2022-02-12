package com.devendrabrain.OrgMgr.controller;

import com.devendrabrain.OrgMgr.bo.StudentBO;
import com.devendrabrain.OrgMgr.constant.ApiName;
import com.devendrabrain.OrgMgr.dto.StudentDTO;
import com.devendrabrain.OrgMgr.service.StudentService;
import com.devendrabrain.OrgMgr.validator.StudentValidator;
import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/OrgMgr/student")
public class StudentOpsController {

    private static final String CLASS_NAME = StudentOpsController.class.getName();

    @Autowired
    StudentService studentService;

    @Autowired
    Environment environment;

    @GetMapping("/fetchAllStudentRecords")
    public List<StudentDTO> fetchAllStudent() {

        MDC.put("X-RS-ApiName", ApiName.ORG_MGR_FETCH_ALL_STUDENT_RECORD.name());

        List<StudentBO> studentBOS = studentService.fetchAllStudent();

        // Transform StudentBO to StudentDTO'
        List<StudentDTO> studentDTOList = studentBOS.stream().map(studentBO -> {
            StudentDTO studentDTO = StudentDTO.builder().id(studentBO.getId()).name(studentBO.getName()).city(studentBO.getCity()).classRoom(studentBO.getClassroom()).build();
            return studentDTO;
        }).collect(Collectors.toList());

        return studentDTOList;
    }

    @PostMapping("/save")
    public StudentDTO saveStudentInfo(@RequestBody StudentDTO studentDTO) {

        MDC.put("X-RS-ApiName", ApiName.ORG_MGR_SAVE_STUDENT_RECORD.name());

        // Validation
        StudentValidator.validateStudentObject(studentDTO, CLASS_NAME);

        // Not good way to do it, need to put this in transformer
        StudentBO.StudentBOBuilder studentBO = StudentBO.builder();
        if(StringUtils.isNotBlank(studentDTO.getCity())) {
            studentBO.city(studentDTO.getCity());
        }
        if(StringUtils.isNotBlank(studentDTO.getClassRoom())) {
            studentBO.classroom(studentDTO.getClassRoom());
        }
        if(StringUtils.isNotBlank(studentDTO.getName())) {
           studentBO.name(studentDTO.getName());
        }

        // Save Student Obj
        StudentBO opStudentBO = studentService.saveStudent(studentBO.build());

        // Transform StudentBO TO StudentDTO
        StudentDTO.StudentDTOBuilder responseObj = StudentDTO.builder();
        responseObj.city(opStudentBO.getCity()).id(opStudentBO.getId()).classRoom(opStudentBO.getClassroom()).name(opStudentBO.getName()).build();

        return responseObj.build();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id) {

        MDC.put("X-RS-ApiName", ApiName.ORG_MGR_DELETE_STUDENT_RECORD.name());

        studentService.deleteStudent(id);

        return "Record for ID "+id+"deleted Successfully";
    }


    @GetMapping("/fetch/record/{id}")
    public StudentDTO fetchStudentRecord(@PathVariable("id") Long id) {

        MDC.put("X-RS-ApiName", ApiName.ORG_MGR_FETCH_STUDENT_RECORD_BY_ID.name());

        // call to fetch Student record by id
        StudentBO opStudentBO = studentService.fetchById(id);

        // Transform StudentBO TO StudentDTO
        StudentDTO.StudentDTOBuilder responseObj = StudentDTO.builder();
        responseObj.city(opStudentBO.getCity()).id(opStudentBO.getId()).classRoom(opStudentBO.getClassroom()).name(opStudentBO.getName()).build();

        return responseObj.build();
    }
}
