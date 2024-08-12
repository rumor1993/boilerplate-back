package com.rumor.yumback.domains.oauth2.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rumor.yumback.common.errors.CustomErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        CustomErrorResponse customErrorResponse = new CustomErrorResponse(HttpServletResponse.SC_UNAUTHORIZED, "계정 권한을 확인해주세요.");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(customErrorResponse);

        response.getWriter().write(jsonResponse);
    }
}
