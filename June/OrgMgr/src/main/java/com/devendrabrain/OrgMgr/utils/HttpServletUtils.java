package com.devendrabrain.OrgMgr.utils;

import com.devendrabrain.OrgMgr.filter.RequestWrapper;
import com.devendrabrain.OrgMgr.filter.ResponseWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class HttpServletUtils {

    protected static final Logger log = LoggerFactory.getLogger(HttpServletUtils.class);

    public static Map<String, String> getHeadersInfo(HttpServletRequest request) {
        Map<String, String> map = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    public static Map<String, String> getHeadersInfo(HttpServletResponse response) {
        Map<String, String> map = new TreeMap<String, String>(String.CASE_INSENSITIVE_ORDER);
        List<String> headerNames = (List<String>) response.getHeaderNames();
        for (String headername : headerNames) {
            map.put(headername, response.getHeader(headername));
        }
        return map;
    }

    public static void writeObjectWithObjMapper(HttpServletResponse response, Object object, ObjectMapper objMapper) {
        response.addHeader("Content-Type", "application/json;charset=UTF-8");
        PrintWriter out;
        try {
            out = response.getWriter();
            out.write(objMapper.writeValueAsString(object));
            out.flush();
        } catch (Exception e) {
            //RSLogUtil.high(e);
        }
    }

    public static String getURL(HttpServletRequest req) {
        String scheme = req.getScheme();
        String serverName = req.getServerName();
        int serverPort = req.getServerPort();
        String contextPath = req.getContextPath();
        String servletPath = req.getServletPath();
        String pathInfo = req.getPathInfo();
        String queryString = req.getQueryString();
        // Reconstruct original requesting URL
        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);
        if (serverPort != 80 && serverPort != 443) {
            url.append(":").append(serverPort);
        }
        url.append(contextPath).append(servletPath);
        if (pathInfo != null) {
            url.append(pathInfo);
        }
        if (queryString != null) {
            url.append("?").append(queryString);
        }
        return url.toString();
    }

    public static String getRequestBody(HttpServletRequest request, Map<String, String> requestHeaders) {
        StringBuilder msg = new StringBuilder();
        if (request instanceof RequestWrapper && !MediaTypeUtils.isFileRequest(requestHeaders)) {
            RequestWrapper requestWrapper = (RequestWrapper) request;
            try {
                String charEncoding = requestWrapper.getCharacterEncoding() != null
                        ? requestWrapper.getCharacterEncoding()
                        : "UTF-8";
                msg.append(new String(requestWrapper.toByteArray(), charEncoding));
            } catch (UnsupportedEncodingException e) {
                log.warn("Failed to parse request payload", e);
            }
        }
        return msg.toString();
    }

    public static String getResponseBody(HttpServletResponse response, Map<String, String> responseMap) {
        StringBuilder msg = new StringBuilder();
        if (!MediaTypeUtils.isFileResponse(responseMap)) {
            ResponseWrapper responseWrapper = (ResponseWrapper) response;
            try {
                msg.append(new String(responseWrapper.toByteArray(), responseWrapper.getCharacterEncoding()));
            } catch (UnsupportedEncodingException e) {
                log.warn("Failed to parse response payload", e);
            }
        }
        return msg.toString();
    }
}