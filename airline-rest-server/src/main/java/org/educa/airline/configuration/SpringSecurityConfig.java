package org.educa.airline.configuration;

import lombok.Getter;
import org.educa.airline.services.SecurityService;
import org.educa.airline.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Getter
@EnableWebSecurity
@Configuration
public class SpringSecurityConfig {

    private final UserService userService;
    private final SecurityService securityService;

    @Autowired
    public SpringSecurityConfig(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    public SecurityService passwordEncoder() {
        return securityService;
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable)
                .httpBasic(withDefaults()).authorizeHttpRequests(
                        request -> request.requestMatchers("/user").anonymous()

                                .requestMatchers(HttpMethod.GET, "/user/{username}").hasAnyRole("admin", "personal", "usuario")
                                .requestMatchers(HttpMethod.DELETE, "/user/{username}").hasAnyRole("admin", "usuario")
                                .requestMatchers(HttpMethod.PUT, "/user/{username}").hasAnyRole("admin", "usuario")
                                .requestMatchers( "/flights/create").hasRole("admin")
                                .requestMatchers(HttpMethod.DELETE, "/flights/{id}").hasRole("admin")
                                .requestMatchers("/flights").authenticated()
                                .requestMatchers(HttpMethod.GET, "/flights/{id}").authenticated()
                                .requestMatchers("/flights/{id}/passenger").hasRole("personal")
                                .requestMatchers(HttpMethod.GET, "/flights/{id}/passenger/{nif}").hasRole("personal")
                                .requestMatchers(HttpMethod.PUT, "/flights/{id}/passenger/{nif}").hasRole("personal")
                                .requestMatchers(HttpMethod.DELETE, "/flights/{id}/passenger/{nif}").hasRole("personal")
                                .requestMatchers("/flights/{id}/passengers}").hasRole("personal")
                                .anyRequest().authenticated());

        return http.build();
    }


}

