package com.benope.apple.config.auth;

import com.benope.apple.config.webMvc.RstCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@RequiredArgsConstructor
public class AuthConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;
    private final CustomUserDetailsService userDetailsService;
    private final TokenProvider tokenProvider;

    private final ObjectMapper objectMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                    .antMatchers("/", "/sso/**", "/auth.**", "/get.**", "/search.**", "/upload.file.reg.db").permitAll()
                    .anyRequest().hasAuthority(AuthorityConstants.Token.ACCESS)
                    .and()
                .formLogin()
                    .disable()
                .httpBasic()
                    .disable()
                .oauth2Login()
                    .authorizationEndpoint()
                        .authorizationRequestRepository(new HttpCookieOAuth2AuthorizationRequestRepository())
                        .and()
                    .userInfoEndpoint()
                        .userService(new OAuth2UserRequestAdapter(userDetailsService))
                        .oidcUserService(new OidcUserRequestAdapter(userDetailsService))
                        .and()
                    .successHandler(successHandler)
                    .failureHandler(failureHandler)
                    .and()
                .logout()
                    .disable()
                .csrf()
                    .disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .exceptionHandling()
                    .authenticationEntryPoint((request, response, authException) -> {
                        response.setCharacterEncoding("UTF-8");
                        response.setContentType("application/json");
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                        response.getWriter().write(
                                objectMapper.writeValueAsString(
                                        Map.of(
                                                "rst_cd", RstCode.UNAUTHORIZED.getCode(),
                                                "rst_msg", RstCode.UNAUTHORIZED.getMessage(),
                                                "success", false
                                        )
                                )
                        );
                    })
                    .accessDeniedHandler((request, response, accessDeniedException) -> {
                        response.setCharacterEncoding("UTF-8");
                        response.setContentType("application/json");
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

                        response.getWriter().write(
                                objectMapper.writeValueAsString(
                                        Map.of(
                                                "rst_cd", RstCode.FORBIDDEN.getCode(),
                                                "rst_msg", RstCode.FORBIDDEN.getMessage(),
                                                "success", false
                                        )
                                )
                        );
                    })
                    .and()
                .cors()
                    .configurationSource(corsConfig())
                    .and()
                .addFilterBefore(new TokenAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
    }

    private CorsConfigurationSource corsConfig() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("https://appleid.apple.com");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
