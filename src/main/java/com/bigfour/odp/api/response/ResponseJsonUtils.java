package com.bigfour.odp.api.response;

import com.bigfour.odp.api.common.JsonUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author : zhujiwu
 * @date : 2019/9/19.
 */
public class ResponseJsonUtils {

    public static void success(Object json, HttpServletResponse response) throws IOException {
        response(json, HttpStatus.OK, response);
    }

    public static void response(Object json, HttpStatus status, HttpServletResponse response) throws IOException {
        response.setStatus(status.value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8.toString());
        JsonUtils.writeJson(response.getOutputStream(), json);
    }

}
