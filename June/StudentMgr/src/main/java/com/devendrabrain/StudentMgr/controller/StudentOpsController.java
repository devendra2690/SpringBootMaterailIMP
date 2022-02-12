package com.devendrabrain.StudentMgr.controller;

import com.devendrabrain.StudentMgr.constant.ApiName;
import com.devendrabrain.StudentMgr.dto.StudentDTO;
import com.devendrabrain.StudentMgr.service.OrgMgrService;
import com.devendrabrain.StudentMgr.utils.ValidationUtils;
import com.devendrabrain.StudentMgr.validator.StudentValidator;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/StudentMgr/studentOps")
public class StudentOpsController {

    private final String CLASS_NAME = StudentOpsController.class.getName();

    @Autowired
    OrgMgrService orgMgrService;

    @GetMapping("/fetchAll")
    public List<StudentDTO> fetchAll() {
        MDC.put("X-RS-ApiName",ApiName.STD_MGR_FETCH_ALL_STUDENT_RECORD.name());
        return orgMgrService.fetchAllStudent();
    }

    @PostMapping("/save/student")
    public StudentDTO addStudent(@RequestBody StudentDTO studentDTO) {

        MDC.put("X-RS-ApiName",ApiName.STD_MGR_SAVE_STUDENT_RECORD.name());

        //Validate Student Object
        StudentValidator.validateStudentObject(studentDTO,CLASS_NAME);

        // Save Student Object
        studentDTO = orgMgrService.saveStudentInfo(studentDTO);

        return studentDTO;
    }

    @DeleteMapping("delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id) {

        MDC.put("X-RS-ApiName", ApiName.STD_MGR_DELETE_STUDENT_RECORD.name());

        String message = orgMgrService.deleteStudent(id);

        return message;
    }

    @GetMapping("/fetch/record/{id}")
    public StudentDTO fetchRecordById(@PathVariable("id") Long id) {

        MDC.put("X-RS-ApiName", ApiName.STD_MGR_FETCH_STUDENT_RECORD_BY_ID.name());

        // call to fetch Student record
        StudentDTO studentDTO = orgMgrService.fetchStudentRecord(id);

        return studentDTO;
    }
}
