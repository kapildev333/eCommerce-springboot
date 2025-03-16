package org.kapildev333.ecommerce.features.authentications;

import jakarta.servlet.http.HttpServletRequest;
import org.kapildev333.ecommerce.utils.CommonApiResponse;
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
    public ResponseEntity<CommonApiResponse<String>> signUp(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("profilePicture") MultipartFile profilePicture) throws IOException {

        Path path = UserService.writeProfilePicture(profilePicture);


        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setProfilePicturePath(path.toString());

        User newUser = userService.signup(user);
        String token = jwtUtil.generateToken(newUser.getEmail());
        return ResponseEntity.ok(new CommonApiResponse<>(true, "User Signed up successfully", token));
    }

    @PostMapping("/v1/signin")
    public ResponseEntity<CommonApiResponse<String>> signIn(@RequestParam("email") String email, @RequestParam("password") String password) {
        try {
            String authenticatedEmail = userService.signIn(email, password);
            if (authenticatedEmail != null) {
                String token = jwtUtil.generateToken(authenticatedEmail);
                return ResponseEntity.ok(new CommonApiResponse<>(true, "User signed in successfully", token));
            } else {
                return ResponseEntity.status(401).body(new CommonApiResponse<>(false, "Invalid email or password", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new CommonApiResponse<>(false, "Invalid email or password", null));
        }
    }

    @PutMapping(value = "/v1/update", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<CommonApiResponse<User>> updateUser(@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("newPassword") String newPassword, @RequestParam("profilePicture") MultipartFile profilePicture) throws IOException {
        String email = jwtUtil.extractEmail(jwtUtil.extractTokenFromRequest());
        User updatedUser = userService.updateUser(email, firstName, lastName, newPassword, profilePicture);
        if (updatedUser != null) {
            return ResponseEntity.ok(new CommonApiResponse<>(true, "User updated successfully", updatedUser));
        } else {
            return ResponseEntity.status(404).body(new CommonApiResponse<>(false, "User not found", null));
        }
    }

    @DeleteMapping("/v1/delete")
    public ResponseEntity<CommonApiResponse<String>> deleteUser() {
        String email = jwtUtil.extractEmail(jwtUtil.extractTokenFromRequest());
        boolean isDeleted = userService.deleteUser(email);
        if (isDeleted) {
            return ResponseEntity.ok(new CommonApiResponse<>(true, "User deleted successfully", null));
        } else {
            return ResponseEntity.status(404).body(new CommonApiResponse<>(false, "User not found", null));
        }
    }
}