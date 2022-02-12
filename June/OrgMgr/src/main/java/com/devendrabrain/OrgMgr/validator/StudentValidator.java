package com.devendrabrain.OrgMgr.validator;

import com.devendrabrain.OrgMgr.constant.ErrorCategory;
import com.devendrabrain.OrgMgr.dto.StudentDTO;
import com.devendrabrain.OrgMgr.utils.ValidationUtils;
import org.apache.commons.lang.StringUtils;

public class StudentValidator {

    public static void validateStudentObject(StudentDTO studentDTO, String className) {

        if(StringUtils.isBlank(studentDTO.getName())) {
            ValidationUtils.getValidationError(ErrorCategory.ER0001,className);
        }
        if(StringUtils.isBlank(studentDTO.getCity())) {
            ValidationUtils.getValidationError(ErrorCategory.ER0002,className);
        }
        if(StringUtils.isBlank(studentDTO.getClassRoom())) {
            ValidationUtils.getValidationError(ErrorCategory.ER0003,className);
        }
    }

}
