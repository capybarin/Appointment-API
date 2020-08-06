package com.example.Appointment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationProvider authProvider;

    /*@Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/users/*").hasRole("TEACHER")
                .antMatchers("/hello").hasRole("STUDENT")
                .antMatchers("/register").permitAll()
                .and()
                .csrf().disable()
                .formLogin().disable();
    }*/

    //@Autowired
    //private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/users/**").hasRole("TEACHER")
<<<<<<< HEAD
                .antMatchers("/appointments/teacher/**").hasRole("TEACHER")
                .antMatchers("/appointments/student/**").hasRole("STUDENT")
=======
>>>>>>> 5fa5052e95126c63a55d1cce8b77f0c61a9793f9
                .antMatchers("/hello").hasRole("STUDENT")
                .antMatchers("/register").permitAll()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable();
    }

}
