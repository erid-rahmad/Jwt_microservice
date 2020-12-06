package com.mpc.sipd.controller;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.mpc.sipd.config.JwtUserDetailsService;
import com.mpc.sipd.jwtmodel.JwtRequest;
import com.mpc.sipd.jwtmodel.JwtResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.mpc.sipd.config.JwtTokenUtil;


@RestController
@CrossOrigin
public class JwtAuthenticationController {
    private static final Logger log = LoggerFactory.getLogger(ControlAut.class);
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;

//    LocalDate localDate = LocalDate.now();



    @RequestMapping(value = "/sipd-bsb/token/getToken", method = RequestMethod.POST)
    public Map<String,?> createAuthenticationToken(@RequestBody Map<String,Object> authenticationRequest ) throws Exception {

//        log.debug("this is authentic {}",authenticationRequest);
//        log.debug("this key {}",authenticationRequest.get("client_id"));
//        log.debug("this key {}",authenticationRequest.get("client_secret"));

        authenticate(authenticationRequest.get("client_id").toString(), authenticationRequest.get("client_secret").toString());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.get("client_id").toString());
        final String token = jwtTokenUtil.generateToken(userDetails);

        JwtResponse response= new JwtResponse(token);
        Map<String,String> respon = new HashMap<>();
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 5);
        dt = c.getTime();

        respon.put("access_token",response.getToken());

        respon.put("expired_in",dt.toString());


        return respon;
    }
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}