package com.vinay.employeeManagementSystem.configuration;

import com.vinay.employeeManagementSystem.JwtFilters.JwtFilter;
import com.vinay.employeeManagementSystem.employeeServices.UserDetailsService;
import com.vinay.employeeManagementSystem.employeeServices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.security.sasl.AuthenticationException;

@Configuration
@EnableWebSecurity
public class SecurityConfig
    {
        @Autowired
        private UserDetailsService userDetailsService;

        @Autowired
        private JwtFilter jwtFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
            return http.csrf(customizer -> customizer.disable())
                        .authorizeHttpRequests(request -> request
                                                .requestMatchers("/register","/login")
                                                .permitAll()
                                                .anyRequest().authenticated())
                        .httpBasic(Customizer.withDefaults())
                        .sessionManagement(session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                        .build();
            //          .formLogin(Customizer.withDefaults())
        }

        //Custom security from database.
        @Bean
        public AuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
            provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
            return provider;
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
            return config.getAuthenticationManager();
        }

            //custom default security
//        @Bean
//        public UserDetailsService userDetailsService(){
//            UserDetails user1 = User.withDefaultPasswordEncoder()
//                    .username("vinay")
//                    .password("9492")
//                    .roles("USER")
//                    .build();
//            UserDetails user2 = User.withDefaultPasswordEncoder()
//                    .username("rakhi")
//                    .password("7090")
//                    .roles("ADMIN")
//                    .build();
//            return new InMemoryUserDetailsManager(user1,user2);
//        }
    }
