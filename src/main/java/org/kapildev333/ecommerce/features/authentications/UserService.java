package org.kapildev333.ecommerce.features.authentications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    static Path writeProfilePicture(MultipartFile profilePicture) throws IOException {
        String folder = "uploads/";
        Path uploadPath = Paths.get(folder);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        byte[] bytes = profilePicture.getBytes();
        Path path = uploadPath.resolve(profilePicture.getOriginalFilename());
        Files.write(path, bytes);
        return path;
    }

    public User signup(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);

    }

    public String signIn(String email, String password) {
        User user = findByEmail(email);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return email;
        }
        return null;
    }

    public User updateUser(String email, String firstName, String lastName, String newPassword, MultipartFile profilePicture) throws IOException {
        User user = findByEmail(email);
        if (user != null) {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            if (newPassword != null && !newPassword.isEmpty()) {
                user.setPassword(passwordEncoder.encode(newPassword));
            }
            if (profilePicture != null && !profilePicture.isEmpty()) {
                Path path = writeProfilePicture(profilePicture);

                user.setProfilePicturePath(path.toString());
            }
            return userRepository.save(user);
        }
        return null;
    }

    public boolean deleteUser(String email) {
        User user = findByEmail(email);
        if (user != null) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }
}