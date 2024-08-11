package com.example.dopamines.global.security;


import com.example.dopamines.domain.user.service.OAuth2Service;
import com.example.dopamines.global.auth.OAuth2AuthenticaitonSuccessHandler;
import com.example.dopamines.global.security.filter.JwtFilter;
import com.example.dopamines.global.security.filter.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final OAuth2AuthenticaitonSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2Service oAuth2Service;

    private final JwtUtil jwtUtil;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://127.0.0.1:5500"); // 허용할 출처
        config.addAllowedOrigin("http://localhost:8081"); // 허용할 출처
        config.addAllowedOrigin("http://localhost:3000"); // 허용할 출처

        config.addAllowedOrigin("http://43.201.50.222:8080"); // 허용할 출처


        config.addAllowedMethod("*"); // 허용할 메서드 (GET, POST, PUT 등)
        config.addAllowedHeader("*"); // 허용할 헤더
        config.setAllowCredentials(true); // 자격 증명 허용
        config.addExposedHeader("Access-Control-Allow-Origin");
        config.addExposedHeader("Authorization"); // 노출할 헤더 추가

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((auth) -> auth.disable());
        http.httpBasic((auth) -> auth.disable());
        //http.formLogin((f) -> f.disable());

        http.authorizeHttpRequests((auth) ->
                auth
//                        .requestMatchers("/test/role-user").hasRole("USER")
//                        .requestMatchers("/test/role-temporary").hasRole("TEMPORARY_USER")
//                        .requestMatchers("/test/role-admin").hasRole("ADMIN")
//                        .requestMatchers("/admin/assign","/admin/user/black").hasRole("ADMIN")
//                        .requestMatchers("/admin/signup").permitAll()
                        .anyRequest().permitAll()
        );

        http.logout((auth) -> auth.deleteCookies("JwtToken", "AToken", "jwt").logoutSuccessUrl("http://localhost:3000/"));
        http.addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class);
        http.addFilterAt(new LoginFilter(jwtUtil, authenticationManager(authenticationConfiguration)), UsernamePasswordAuthenticationFilter.class);


//        http.sessionManagement((session) -> {
//            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//            session.sessionFixation().none();
//        });
        http.oauth2Login((config) -> {
            config.successHandler(oAuth2AuthenticationSuccessHandler);
            config.userInfoEndpoint((endpoint) -> endpoint.userService(oAuth2Service));
        });


        return http.build();
    }


    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
