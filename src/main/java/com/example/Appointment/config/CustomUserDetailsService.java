package com.example.Appointment.config;


import com.example.Appointment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.example.Appointment.entity.User user= userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Unknown user: "+email);
        }
        UserDetails tmpUser = User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
<<<<<<< HEAD
                .roles(user.getRole_id().getName())
=======
                .roles(user.getRole().getName())
>>>>>>> 5fa5052e95126c63a55d1cce8b77f0c61a9793f9
                .build();
        return tmpUser;
    }
}