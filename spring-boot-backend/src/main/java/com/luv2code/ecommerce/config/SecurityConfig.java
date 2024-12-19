package com.luv2code.ecommerce.config;


import com.luv2code.ecommerce.util.JwtAuthenticationFilter;
import com.luv2code.ecommerce.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless authentication
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()  // Open access for authentication endpoints
                .antMatchers("/api/**").permitAll()
                .anyRequest().authenticated()  // Secure all other endpoints
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST"));
        corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        corsConfig.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }




//    private final UserService userService;
//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public SecurityConfig(UserService userService, PasswordEncoder passwordEncoder) {
//        this.userService = userService;
//        this.passwordEncoder = passwordEncoder;
//    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> auth
//                        .antMatchers("/auth/**").permitAll()
//                        .antMatchers("/api/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(withDefaults())
//                .cors();
//
//        return http.build();
//    }
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .cors().and()
//                .csrf(AbstractHttpConfigurer::disable)
//                .securityContext(securityContext -> securityContext
//                        .securityContextRepository(new HttpSessionSecurityContextRepository())
//                )
//                .authorizeHttpRequests(auth -> auth
//                        .antMatchers("/auth/**").permitAll()// Allow all requests to /auth/**
//                        .antMatchers("/api/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // Allow session persistence
//                )
//                .formLogin()
//                .loginProcessingUrl("/auth/login") // Set custom login page URL
//                .successHandler((request, response, authentication) -> {
//                    // Respond with HTTP 200 OK and custom message
//                    response.setStatus(HttpServletResponse.SC_OK);
//                    response.setContentType("application/json");
//                    response.getWriter().write("{\"message\": \"Login successful\"}");
//                })
//                .failureHandler((request, response, exception) -> {
//                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                    response.setContentType("application/json");
//                    response.getWriter().write("{\"error\": \"Invalid credentials\"}");
//                })
//                .permitAll() // Allow everyone to access the login page
//                .and()
//                .logout()
//                .invalidateHttpSession(true)
//                .logoutUrl("/auth/logout")
//                .deleteCookies("JSESSIONID")
//                .permitAll();
//
//        return http.build();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration corsConfig = new CorsConfiguration();
//        corsConfig.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
//        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST"));
//        corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
//        corsConfig.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfig);
//        return source;
//    }
//
//    @Autowired
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
//    }
}


