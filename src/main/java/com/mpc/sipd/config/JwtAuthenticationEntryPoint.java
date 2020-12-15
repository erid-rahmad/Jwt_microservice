package com.mpc.sipd.config;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -7858869558953243875L;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        Map<String, Object> response_ = new HashMap<>();
        Map<String, Object> response_1 = new HashMap<>();

        log.info("this code {} this respon{} this {}",authException.getMessage(),response.getStatus());

        String exception = authException.getMessage();

        if (exception.contains("Bad")){
//            System.out.println(" Token has eror");
            response_.put("code", "298");
            response_.put("message","Invalid Login");
            response_1.put("status",response_);
            response.setHeader("Content-type","application/json");
            response.getWriter().write(new ObjectMapper().writeValueAsString(response_1));
            log.info(String.valueOf(response_));
        }
        else if (exception.contains("access")){
//            System.out.println(" Token has eror");
            response_.put("code", "299");
            response_.put("message","Token has expired");
            response_1.put("status",response_);
            response.setHeader("Content-type","application/json");
            response.getWriter().write(new ObjectMapper().writeValueAsString(response_1));
            log.info(String.valueOf(response_));
        }
    }
}