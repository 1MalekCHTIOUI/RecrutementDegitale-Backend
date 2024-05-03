package com.tekup.recrutement.controllers;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tekup.recrutement.dao.UserRepository;
import com.tekup.recrutement.dto.AuthenticationRequest;

import com.tekup.recrutement.entities.User;
import com.tekup.recrutement.services.JwtService;
import com.tekup.recrutement.services.UserDetailsService;
import com.tekup.recrutement.services.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*")
@RestController
public class AuthenticationController {
    @Autowired
    UserService userService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtService jwtService;

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    @PostMapping("/signin")
    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
            HttpServletResponse response)
            throws IOException, DisabledException, UsernameNotFoundException, BadCredentialsException, JSONException,
            ServletException {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("incorrect username or password");
        } catch (DisabledException disabledException) {
            response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE, "user not activated");

        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        User user = userRepository.findByEmail(userDetails.getUsername());
        final String jwt = jwtService.generateToken(authenticationRequest.getUsername());

        response.addHeader("Access-Control-Expose-Headers", "Authorization");
        response.addHeader("Access-Control-Allow-Headers", "Authorization");
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
        ObjectMapper mapper = new ObjectMapper();
        @SuppressWarnings("unchecked")
        Map<String, Object> userMap = mapper.convertValue(user, Map.class);
        userMap.remove("password"); // remove the password
        String userJson = mapper.writeValueAsString(userMap);

        response.getWriter().write(new JSONObject().put("user", new JSONObject(userJson)).toString());

    }
}
