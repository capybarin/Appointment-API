package com.example.Appointment.config;

//import com.example.Appointment.entity.User;
import com.example.Appointment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
<<<<<<< HEAD
=======
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
>>>>>>> 5fa5052e95126c63a55d1cce8b77f0c61a9793f9
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

<<<<<<< HEAD
=======
import java.util.ArrayList;
import java.util.List;


>>>>>>> 5fa5052e95126c63a55d1cce8b77f0c61a9793f9

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();
        com.example.Appointment.entity.User myUser = userRepository.findByEmail(userName);
        if (myUser == null) {
            throw new BadCredentialsException("Unknown user "+userName);
        }
        if (!password.equals(myUser.getPassword())) {
            throw new BadCredentialsException("Bad password");
        }
        UserDetails principal = User.builder()
                .username(myUser.getEmail())
                .password(myUser.getPassword())
<<<<<<< HEAD
                .roles(myUser.getRole_id().getName())
=======
                .roles(myUser.getRole().getName())
>>>>>>> 5fa5052e95126c63a55d1cce8b77f0c61a9793f9
                .build();
        return new UsernamePasswordAuthenticationToken(
                principal, password, principal.getAuthorities());
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
