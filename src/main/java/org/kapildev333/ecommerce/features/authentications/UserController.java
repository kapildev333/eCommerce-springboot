package org.kapildev333.ecommerce.features.authentications;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.kapildev333.ecommerce.utils.ApiResponse;
import org.kapildev333.ecommerce.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping(value = "/v1/signup", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponse<String>> signup(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("profilePicture") MultipartFile profilePicture) throws IOException {

        Path path = UserService.writeProfilePicture(profilePicture);


        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setProfilePicturePath(path.toString());

        User newUser = userService.signup(user);
        String token = jwtUtil.generateToken(newUser.getEmail());
        return ResponseEntity.ok(new ApiResponse<>(true, "User Signed up successfully", token));
    }

    @PostMapping("/v1/signin")
    public ResponseEntity<ApiResponse<String>> signin(@RequestParam("email") String email, @RequestParam("password") String password) {
        try {
            String authenticatedEmail = userService.signIn(email, password);
            if (authenticatedEmail != null) {
                String token = jwtUtil.generateToken(authenticatedEmail);
                return ResponseEntity.ok(new ApiResponse<>(true, "User signed in successfully", token));
            } else {
                return ResponseEntity.status(401).body(new ApiResponse<>(false, "Invalid email or password", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new ApiResponse<>(false, "Invalid email or password", null));
        }
    }

    @PutMapping(value = "/v1/update", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ApiResponse<User>> updateUser(@RequestHeader("Authorization") String token, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("newPassword") String newPassword, @RequestParam("profilePicture") MultipartFile profilePicture) throws IOException {
        String email = jwtUtil.extractEmail(token.substring(7));
        User updatedUser = userService.updateUser(email, firstName, lastName, newPassword, profilePicture);
        if (updatedUser != null) {
            return ResponseEntity.ok(new ApiResponse<>(true, "User updated successfully", updatedUser));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "User not found", null));
        }
    }

    @DeleteMapping("/v1/delete")
    public ResponseEntity<ApiResponse<String>> deleteUser(@RequestHeader(name = "Authorization") String token) {
        String email = jwtUtil.extractEmail(token.substring(7));
        boolean isDeleted = userService.deleteUser(email);
        if (isDeleted) {
            return ResponseEntity.ok(new ApiResponse<>(true, "User deleted successfully", null));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "User not found", null));
        }
    }
}