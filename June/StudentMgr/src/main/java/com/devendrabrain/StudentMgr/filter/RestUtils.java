package com.devendrabrain.StudentMgr.filter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestUtils {

    private static final Logger log = LoggerFactory.getLogger(RestUtils.class);

    public static String base64BasicAuthorization(String username, String password) {
        String plainCredential = username + ":" + password;
        byte[] plainCredentialBytes = plainCredential.getBytes();
        byte[] base64CredentialBytes = Base64.getEncoder().encode((plainCredentialBytes));
        String base64Credential = new String(base64CredentialBytes);
        return base64Credential;
    }

    public static String base64BasicDecoder(String base64Credential) {
        byte[] plainCredentialBytes = Base64.getDecoder().decode(base64Credential);
        String plainCredential = new String(plainCredentialBytes);
        return plainCredential;
    }

    public static boolean validateBasicAuthorization(String base64String) {
        if (StringUtils.isBlank(base64String)) {
            log.error("base64String is null");
            return false;
        }
        String credentials = null;
        try {
            credentials = new String(Base64.getDecoder().decode(base64String), Charset.forName("UTF-8"));
        } catch (Exception e) {
            log.error("Could not decode base64 auth String");
            return false;
        }
        final String[] values = credentials.split(":", 2);
        if (values.length == 2) {
            return true;
        }
        return false;
    }

    public static String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static String requestParamsString(Map<String, String[]> params) {
        String paramString = "";
        for (Entry<String, String[]> a : params.entrySet()) {
            paramString = StringUtils.isBlank(paramString) ? "" : paramString + "&";
            paramString = paramString + a.getKey() + "=" + Arrays.toString(a.getValue());
        }
        return paramString;
    }

    public static String urlEncodeUTF8(Map<?, ?> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            if (entry.getValue() instanceof List<?>) {
                String listOfIds = ((List<?>) entry.getValue()).stream().map(Object::toString)
                        .collect(Collectors.joining(","));
                sb.append(String.format("%s=%s", urlEncodeUTF8(entry.getKey().toString()), urlEncodeUTF8(listOfIds)));
            } else {
                sb.append(String.format("%s=%s", urlEncodeUTF8(entry.getKey().toString()),
                        urlEncodeUTF8(entry.getValue().toString())));
            }
        }
        return sb.toString();
    }

    public static String base64BasicEncoder(String plainCredential) {
        byte[] plainCredentialBytes = plainCredential.getBytes();
        byte[] base64CredentialBytes = Base64.getEncoder().encode((plainCredentialBytes));
        String base64Credential = new String(base64CredentialBytes);
        return base64Credential;
    }

}
