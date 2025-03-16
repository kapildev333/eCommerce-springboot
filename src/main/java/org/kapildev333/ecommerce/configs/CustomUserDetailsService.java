package org.kapildev333.ecommerce.configs;

import org.kapildev333.ecommerce.features.authentications.User;
import org.kapildev333.ecommerce.features.authentications.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        // Returning a Spring Security UserDetails object with roles, username, password, etc.
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword()) // Assuming password is encoded
                .authorities(user.getRoles()) // Assuming roles are properly formatted
                .build();
    }
}

