package com.prince.movieezapi.shared.utilities;

import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

@Service public class BasicUtils {

  @Value("${app.email.regexp}")
  private String emailRegex;

  private final ObjectMapper OBJECT_MAPPER = JsonMapper.shared();

  @SneakyThrows
  public void sendJson(HttpStatus status, Object message, HttpServletResponse response) {
    response.setStatus(status.value());
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(OBJECT_MAPPER.writeValueAsString(message));
  }

  public boolean isValidEmail(String email) {
    return email.matches(emailRegex);
  }
}
