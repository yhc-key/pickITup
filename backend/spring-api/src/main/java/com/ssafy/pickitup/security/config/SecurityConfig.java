package com.ssafy.pickitup.security.config;


import com.ssafy.pickitup.security.filter.JwtAuthenticationFilter;
import com.ssafy.pickitup.security.handler.JwtAccessDeniedHandler;
import com.ssafy.pickitup.security.handler.OAuth2LoginFailureHandler;
import com.ssafy.pickitup.security.handler.OAuth2LoginSuccessHandler;
import com.ssafy.pickitup.security.jwt.JwtAuthenticationEntryPoint;
import com.ssafy.pickitup.security.jwt.JwtTokenProvider;
import com.ssafy.pickitup.security.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity  // @Secure, @PreAuthorize, @PostAuthorize 사용가능
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] swaggerURL = {
        "/api/**", "/graphiql", "/graphql",
        "/swagger-ui/**", "/api-docs", "/swagger-ui.html",
        "/v3/api-docs/**", "/api-docs/**", "/swagger-ui.html"
    };
    private final CorsConfig corsConfig;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
            .requestMatchers("/auth/signup", "/auth/login", "auth/logout",
                "/auth/token/refresh", "/recruit/**",
                "/keywords/**", "/users/test/**", "/quizzes/ox/**",
                "/quizzes/speed/**", "/users/mongo") // '인증','인가' 서비스 적용x
            .requestMatchers(swaggerURL)
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()); // 정적 리소스들
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 기본 세팅
        http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()));

        // JWT 토큰 인증 설정
        http
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler));

        // URL별 권한 설정
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/favicon.ico", "/error")
                .permitAll() // '인증' 무시
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers(swaggerURL).permitAll()
                .requestMatchers("/users/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated());
        // Oauth 로그인 설정
        http
            .oauth2Login(oauth2Login -> oauth2Login
                .successHandler(oAuth2LoginSuccessHandler)
                .failureHandler(oAuth2LoginFailureHandler)
                .userInfoEndpoint(userInfoEndpoint ->
                    userInfoEndpoint.userService(customOAuth2UserService)));
        return http.build();
    }
}
