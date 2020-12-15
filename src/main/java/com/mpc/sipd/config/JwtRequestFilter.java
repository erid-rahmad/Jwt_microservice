package com.mpc.sipd.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.RedirectException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;
@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("X-Token");
        log.info("this requestTokenHeader {} ",requestTokenHeader);
        String username = null;
        String jwtToken = null;


        if (requestTokenHeader != null ) {
            jwtToken = requestTokenHeader;
            log.info("this jwtoken {} ",jwtToken);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                log.info("this username {}",username);


            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
//                Map<String, Object> response_ = new HashMap<>();
//                Map<String, Object> response_1 = new HashMap<>();
//                System.out.println("JWT Token has expired");
//                response_.put("code", "299");
//                response_.put("reason","Token has expired");
//                response_1.put("status",response_);
//                response.setHeader("Content-type","application/json");
//                response.getWriter().write(new ObjectMapper().writeValueAsString(response_1
//                ));
//                log.info(String.valueOf(response_));


            }

        }
        else  {
            logger.warn("JWT Token does not begin with Bearer String");
//            Map<String, Object> response_ = new HashMap<>();
//            System.out.println("Token invalid ");
//            response_.put("code", "401");
//            response_.put("reason","access token is empty");
//            response.setHeader("Content-type","application/json");
//            response.getWriter().write(new ObjectMapper().writeValueAsString(response_
//            ));
//            log.info(String.valueOf(response_));
        }
        // Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
            // if token is valid configure Spring Security to manually set
            // authentication
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}