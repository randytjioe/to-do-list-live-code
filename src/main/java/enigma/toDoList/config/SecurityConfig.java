package enigma.toDoList.config;

import enigma.toDoList.model.Role;
import enigma.toDoList.security.JwtAuthenticationFilter;
import enigma.toDoList.security.UserSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final UserSecurity userSecurity;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers("/api/auth/**").permitAll()
                         .requestMatchers("/api/admin/super-admin").permitAll()


                        // Todo endpoints
                        .requestMatchers(HttpMethod.POST, "/api/todos").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/todos", "/api/todos/*").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/todos/*").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/api/todos/*/status").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/todos/*").authenticated()
//
                        // User endpoints
                        .requestMatchers(HttpMethod.GET, "/api/admin/users","/api/admin/users/{id}").access(adminAuthorizationManager())
                        .requestMatchers(HttpMethod.PATCH, "/api/admin/users/*").access(superAdminAuthorizationManager())
                        .requestMatchers(HttpMethod.GET, "/api/admin/todos").access(superAdminAuthorizationManager())
                        .requestMatchers(HttpMethod.GET, "/api/admin/todos").access(adminAuthorizationManager())
                                .requestMatchers(HttpMethod.GET, "/api/admin/todos/*").access(superAdminAuthorizationManager())
                                .requestMatchers(HttpMethod.GET, "/api/admin/todos/*").access(adminAuthorizationManager())

                        // Any other request
                        .anyRequest().authenticated()
                )
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthorizationManager<RequestAuthorizationContext> adminAuthorizationManager() {
        return (authentication, context) -> {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"))) {
                return new AuthorizationDecision(true);
            }

            return new AuthorizationDecision(false);
        };
    }

    public AuthorizationManager<RequestAuthorizationContext> superAdminAuthorizationManager() {
        return (authentication, context) -> {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            if (auth != null && auth.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("SUPER_ADMIN"))) {
                return new AuthorizationDecision(true);
            }
            return new AuthorizationDecision(false);
        };
    }

}
