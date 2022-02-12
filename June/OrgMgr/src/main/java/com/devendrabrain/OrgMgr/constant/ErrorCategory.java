package com.devendrabrain.OrgMgr.constant;

public enum ErrorCategory {

    ValidationError("1001", "Data Validation Error"),

    ER0001("ER0001","Name is required"),
    ER0002("ER0002","City is required"),
    ER0003("ER0003","ClassRoom is required"),
    ER0004("ER0004","Student record does not exist with given Id");;

    private String code;
    private String message;

    ErrorCategory(String code, String message) {
        this.code = code;
        this.message = message;
    }
    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
