package com.prince.movieezapi.shared.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;

public class BasicUtils {

    private static final  ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @SneakyThrows
    public static void sendJson(HttpStatus status, Object message, HttpServletResponse response) {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(OBJECT_MAPPER.writeValueAsString(message));
    }
}
