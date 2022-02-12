package com.devendrabrain.StudentMgr.service;

import com.devendrabrain.StudentMgr.config.LoadBalancerConfiguration;
import com.devendrabrain.StudentMgr.dto.StudentDTO;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@FeignClient(name = "spring-org-mgr", url = "localhost:8381")
@FeignClient(name = "spring-api-gateway")
@LoadBalancerClient(name = "spring-api-gateway",configuration = LoadBalancerConfiguration.class)
public interface OrgMgrService {

    @GetMapping("/OrgMgr/student/fetchAllStudentRecords")
    public List<StudentDTO> fetchAllStudent();

    @PostMapping("/OrgMgr/student/save")
    public StudentDTO saveStudentInfo(@RequestBody StudentDTO studentDTO);

    @DeleteMapping("/OrgMgr/student/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id);

    @GetMapping("/OrgMgr/student/fetch/record/{id}")
    public StudentDTO fetchStudentRecord(@PathVariable("id") Long id);
}
