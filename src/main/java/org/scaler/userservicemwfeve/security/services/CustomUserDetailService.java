package org.scaler.userservicemwfeve.security.services;

import org.scaler.userservicemwfeve.models.User;
import org.scaler.userservicemwfeve.repositories.UserRepository;
import org.scaler.userservicemwfeve.security.models.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(username);
        if(userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User by Email: " + username + " doesn't exist");
        }
        CustomUserDetails userDetails = new CustomUserDetails(userOptional.get());

        return userDetails;
    }
}
