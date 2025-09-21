package org.example.academic_portal_system.controller;

import org.example.academic_portal_system.dto.UserDTO;
import org.example.academic_portal_system.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RefreshTokenController {
    @Autowired
    private JwtUtil jwtUtil;

    // Endpoint to refresh the access token using a refresh token
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody String refreshToken) {
        try {
//            // Validate the refresh token
//            if (!jwtUtil.validateRefreshToken(refreshToken)) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token expired or invalid");
//            }

            // Extract user email from refresh token
            String email = jwtUtil.getUsernameFromToken(refreshToken);

            // Typically, retrieve user from the database using the email
            UserDTO userDTO = new UserDTO(); // Replace with actual user lookup logic

            // Generate new access token using user details
            String newAccessToken = jwtUtil.generateToken(userDTO);

            // Return new access token in response
            Map<String, String> response = new HashMap<>();
            response.put("accessToken", newAccessToken);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }
    }
}
