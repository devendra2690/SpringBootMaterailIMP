package com.devendrabrain.OrgMgr.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Map;

public class MediaTypeUtils {

    /*
     * Return true if contentType is CSV
     */
    public static boolean isCSVMediaTypeResponse(Map<String, String> responseMap) {
        return responseMap != null && !CollectionUtils.isEmpty(responseMap)
                && StringUtils.isNotBlank(responseMap.get("content-type"))
                && responseMap.get("content-type").toLowerCase().startsWith("application/csv");
    }

    /*
     * Return true if contentType is PDF
     */
    public static boolean isPDFMediaTypeRequest(Map<String, String> requestMap) {
        return (requestMap != null && !CollectionUtils.isEmpty(requestMap) && requestMap.get("Accept") != null
                && requestMap.get("Accept").toLowerCase().startsWith("application/pdf"));
    }

    /*
     * Return true if contentType is PDF
     */
    public static boolean isPDFMediaTypeResponse(Map<String, String> responseMap) {
        return (responseMap != null && !CollectionUtils.isEmpty(responseMap) && responseMap.get("Accept") != null
                && responseMap.get("Accept").toLowerCase().startsWith("application/pdf"))
                || (responseMap != null && !CollectionUtils.isEmpty(responseMap)
                && StringUtils.isNotBlank(responseMap.get("content-type"))
                && responseMap.get("content-type").toLowerCase().startsWith("application/pdf"));
    }

    /*
     * Return true if contentType is multipart
     */
    public static boolean isMultipartRequest(Map<String, String> requestMap) {
        return requestMap != null && !CollectionUtils.isEmpty(requestMap)
                && StringUtils.isNotBlank(requestMap.get("content-type"))
                && requestMap.get("content-type").toLowerCase().startsWith("multipart/form-data");
    }

    /*
     * Return true if contentType is PDF or CSV
     */
    public static boolean isFileResponse(Map<String, String> responseMap) {
        if (isCSVMediaTypeResponse(responseMap) || isPDFMediaTypeResponse(responseMap)) {
            return true;
        }
        return false;
    }

    public static Boolean isFileRequest(Map<String, String> requestMap) {
        if (isMultipartRequest(requestMap) || isPDFMediaTypeRequest(requestMap)) {
            return true;
        }
        return false;
    }
}

