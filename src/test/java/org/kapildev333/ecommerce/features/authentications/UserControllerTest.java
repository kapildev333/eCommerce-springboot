package org.kapildev333.ecommerce.features.authentications;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kapildev333.ecommerce.utils.JwtUtil;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testSignup() throws Exception {
        MockMultipartFile profilePicture = new MockMultipartFile("profilePicture", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "test image".getBytes());

        when(userService.signup(any(User.class))).thenReturn(new User());
        when(jwtUtil.generateToken(any(String.class))).thenReturn("test-token");

        mockMvc.perform(multipart("/api/user/v1/signup")
                        .file(profilePicture)
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("email", "john.doe@example.com")
                        .param("password", "password"))
                .andExpect(status().isOk());
    }

    @Test
    public void testSignin() throws Exception {
        when(userService.signIn("john.doe@example.com", "password")).thenReturn("john.doe@example.com");
        when(jwtUtil.generateToken("john.doe@example.com")).thenReturn("test-token");

        mockMvc.perform(post("/api/user/v1/signin")
                        .param("email", "john.doe@example.com")
                        .param("password", "password"))
                .andExpect(status().isOk());
    }

    @Test
    public void testSigninInvalidCredentials() throws Exception {
        when(userService.signIn("john.doe@example.com", "wrongpassword")).thenReturn(null);

        mockMvc.perform(post("/api/user/v1/signin")
                        .param("email", "john.doe@example.com")
                        .param("password", "wrongpassword"))
                .andExpect(status().isUnauthorized());
    }
}