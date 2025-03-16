package com.demo.taskapprovalsystem.controller;

import com.demo.taskapprovalsystem.request.LoginRequest;
import com.demo.taskapprovalsystem.response.JwtResponse;
import com.demo.taskapprovalsystem.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * Handles user login requests and authenticates the user.
     *
     * This method receives the login credentials (username/email and password) in the form of a
     * **`LoginRequest`** object. It passes the credentials to the **`loginService`** for authentication.
     * If the authentication is successful, it generates a **JWT (JSON Web Token)** and returns it in the
     * response as a **`JwtResponse`**.
     *
     * @param loginRequest The login credentials containing the user's username/email and password.
     *                     This object is passed as the request body.
     * @return A **ResponseEntity** containing a **JwtResponse** with the generated JWT token if the login is successful.
     *         If authentication fails, an appropriate error response is returned (error handling not shown here).
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String jwt = loginService.authenticateUser(loginRequest);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}



