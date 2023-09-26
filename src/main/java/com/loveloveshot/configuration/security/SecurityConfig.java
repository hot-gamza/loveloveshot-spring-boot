package com.loveloveshot.configuration.security;

import com.loveloveshot.configuration.properties.AppProperties;
import com.loveloveshot.configuration.properties.CorsProperties;
import com.loveloveshot.oauth.application.dto.handler.OAuth2AuthenticationFailHandler;
import com.loveloveshot.oauth.application.dto.handler.OAuth2AuthenticationSuccessHandler;
import com.loveloveshot.oauth.application.dto.handler.TokenAccessDeniedHandler;
import com.loveloveshot.oauth.application.service.CustomOAuth2UserService;
import com.loveloveshot.oauth.application.service.CustomUserDetailsService;
import com.loveloveshot.oauth.domain.aggregate.enumType.RoleType;
import com.loveloveshot.oauth.domain.exception.RestAuthenticationEntryPoint;
import com.loveloveshot.oauth.domain.repository.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.loveloveshot.oauth.filter.TokenAuthenticationFilter;
import com.loveloveshot.oauth.token.AuthTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CorsProperties corsProperties;
    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;
    private final CustomOAuth2UserService oAuth2UserService;
    private final TokenAccessDeniedHandler tokenAccessDeniedHandler;


    // UserDetailsService 설정
    @Bean
    protected AuthenticationProvider authenticationProvider() {
        System.out.println("authenticationProvider 들어옴 ");
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    @Bean()
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("filterChain 설정 ::::: ");
        http
                .cors()// CORS 허용
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용x
                .and()
                .csrf().disable() // csrf 비활성화
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .accessDeniedHandler(tokenAccessDeniedHandler)  // 인증되지 않은 요청에 대한 처리
                .and()

                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/*").permitAll()
                .antMatchers("/api/**").hasRole(RoleType.USER.getCode())
                .antMatchers("/api/**/admin/**").hasRole(RoleType.ADMIN.getCode())
//                .anyRequest().authenticated()
                .and()

                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorization")   // 인가 엔드포인트 URI 설정
                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())    // 인증 요청 정보를 쿠키에 저장하도록 설정
                .and()

                .redirectionEndpoint()
                .baseUri("/*/oauth2/code/*")
                .and()
                .userInfoEndpoint()
                .userService(oAuth2UserService)
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler())
                .failureHandler(oAuth2AuthenticationFailureHandler());

//        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(tokenAuthenticationFilter(), OAuth2LoginAuthenticationFilter.class);
        return http.build();
    }


    // Auth 매니저 설정
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    // security 설정 시, 사용할 인코더 설정
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 토큰 필터 설정
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    /**
     * 쿠키 기반 인가 Repository
     *  인가 응답을 연계하고 검증할 때 사용
     */
    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    // Oauth 인증 성공 핸들러
    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(
                tokenProvider,
                appProperties,
                oAuth2AuthorizationRequestBasedOnCookieRepository()
        );
    }

    // Oauth 인증 실패 핸들러
    @Bean
    public OAuth2AuthenticationFailHandler oAuth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationFailHandler(oAuth2AuthorizationRequestBasedOnCookieRepository());
    }


    // CORS 설정
//    @Bean
//    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
//        UrlBasedCorsConfigurationSource corsConfigSource = new UrlBasedCorsConfigurationSource();
//
//        CorsConfiguration corsConfig = new CorsConfiguration();
//        corsConfig.setAllowedHeaders(Arrays.asList(corsProperties.getAllowedHeaders().split(",")));
//        corsConfig.setAllowedMethods(Arrays.asList(corsProperties.getAllowedMethods().split(",")));
//        corsConfig.setAllowedOrigins(Arrays.asList(corsProperties.getAllowedOrigins().split(",")));
//        corsConfig.setAllowCredentials(true);
//        corsConfig.setMaxAge(corsConfig.getMaxAge());
//
//        corsConfigSource.registerCorsConfiguration("/**", corsConfig);
//        return corsConfigSource;
//    }
}