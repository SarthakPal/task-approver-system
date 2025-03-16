package com.demo.taskapprovalsystem.service;

import com.demo.taskapprovalsystem.request.LoginRequest;
import com.demo.taskapprovalsystem.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * Authenticates the user by validating their credentials (username/email and password).
     *
     * This method receives the login credentials in the form of a **`LoginRequest`** object containing the
     * username and password. It then attempts to authenticate the user using the **`authenticationManager`**.
     * If the credentials are valid, the authentication is stored in the **`SecurityContextHolder`**. Finally,
     * a **JWT (JSON Web Token)** is generated and returned for the authenticated user.
     *
     * @param loginRequest The login credentials of the user, which includes the username/email and password.
     * @return A **String** representing the generated JWT token for the authenticated user.
     *         The token can be used for subsequent authenticated requests.
     * @throws ** AuthenticationException ** If the authentication fails (e.g., invalid credentials),
     *                                 an exception will be thrown by the **`authenticationManager`**.
     */
    public String authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(loginRequest.getUserName());
    }

}
