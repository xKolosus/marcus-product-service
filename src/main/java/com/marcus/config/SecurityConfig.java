package com.marcus.config;

import static com.marcus.user.domain.model.Role.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import com.marcus.config.constant.ApiUrlConstant;
import com.marcus.config.middleware.JwtAuthenticationFilter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

  private static final String[] WHITE_LIST_URL = {
    ApiUrlConstant.AUTH_URL + ApiUrlConstant.ALL_URLS,
    ApiUrlConstant.AUTH_URL + ApiUrlConstant.ONE_PATH_URL,
    "/v3/api-docs",
    "/v3/api-docs/**",
    "/swagger-ui/**",
    "/swagger-ui.html"
  };
  private final JwtAuthenticationFilter jwtAuthFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.addFilterBefore(new CorsFilter(corsConfigurationSource()), ChannelProcessingFilter.class)
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            req ->
                req.requestMatchers(WHITE_LIST_URL)
                    .permitAll()
                    .requestMatchers(
                        GET,
                        ApiUrlConstant.CATEGORY_URL + ApiUrlConstant.ALL_URLS,
                        ApiUrlConstant.CATEGORY_URL)
                    .permitAll()
                    .requestMatchers(
                        GET,
                        ApiUrlConstant.PRODUCT_URL + ApiUrlConstant.ALL_URLS,
                        ApiUrlConstant.PRODUCT_URL)
                    .permitAll()
                    .requestMatchers(ApiUrlConstant.PRODUCT_URL + ApiUrlConstant.SEARCH_PATH)
                    .permitAll()
                    .requestMatchers(
                        ApiUrlConstant.USER_URL + ApiUrlConstant.ALL_URLS, ApiUrlConstant.USER_URL)
                    .hasRole(ADMIN.name())
                    .requestMatchers(POST, ApiUrlConstant.CATEGORY_URL, ApiUrlConstant.PRODUCT_URL)
                    .hasRole(ADMIN.name())
                    .requestMatchers(PUT, ApiUrlConstant.CATEGORY_URL, ApiUrlConstant.PRODUCT_URL)
                    .hasRole(ADMIN.name())
                    .requestMatchers(
                        DELETE,
                        ApiUrlConstant.CATEGORY_URL + ApiUrlConstant.ALL_URLS,
                        ApiUrlConstant.PRODUCT_URL + ApiUrlConstant.ALL_URLS)
                    .hasRole(ADMIN.name())
                    .requestMatchers(
                        ApiUrlConstant.PURCHASE_URL,
                        ApiUrlConstant.PURCHASE_URL + ApiUrlConstant.ALL_URLS)
                    .hasRole(USER.name())
                    .anyRequest()
                    .authenticated())
        .exceptionHandling(
            configurer ->
                configurer.authenticationEntryPoint(
                    new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
        .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("*"));
    configuration.setAllowedMethods(List.of("*"));
    configuration.setAllowedHeaders(List.of("*"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
