package com.parket.webproject.cofig;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // CSRF 비활성화 (필요하면 켜고 CSRF 토큰 처리 추가)
                .csrf(csrf -> csrf.disable())

                // 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // 업로드 이미지 허용
                        .requestMatchers("/uploads/**").permitAll()

                        // 공용 API
                        .requestMatchers("/api/authentication/check").permitAll()

                        // 로그인 / 회원가입
                        .requestMatchers(HttpMethod.POST, "/member/register", "/loginProcess").permitAll()
                        .requestMatchers(HttpMethod.GET, "/member/login").permitAll()

                        // 공지사항 조회는 모두 허용
                        .requestMatchers("/noti/list", "/noti/detail/**").permitAll()

                        // 공지사항(수정, 삭제) 관리자만 허용
                        .requestMatchers("/noti/noticeAdd", "/noti/noticeModify/**", "/noti/delete/**")
                        .hasRole("ADMIN")

                        // 관리자 페이지
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")

                        // 메뉴 관리 (관리자 + 매니저)
                        .requestMatchers(HttpMethod.POST, "/menu/add", "/menu/update").hasAnyAuthority("ADMIN", "MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/menu/delete").hasAnyAuthority("ADMIN", "MANAGER")

                        // 장바구니/주문은 로그인 필요
                        .requestMatchers("/cart/add").authenticated()
                        .requestMatchers("/order/**").authenticated()

                        // 일반적인 GET 요청은 모두 허용 (프론트 페이지 조회용)
                        .requestMatchers(HttpMethod.GET, "/**").permitAll()

                        // 그 외 모든 요청은 인증 필요
                        .anyRequest().authenticated()
                )

                // 로그인 설정
                .formLogin(formLoginConfig -> formLoginConfig
                        .loginPage("/member/login")
                        .loginProcessingUrl("/loginProcess")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )

                // 로그아웃 설정
                .logout(logoutConfig -> logoutConfig
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                )

                // 인증 실패 시 401 응답
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> response.setStatus(401))
                );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 기본 정적 리소스 (/static, /public 등) 허용
    @Bean
    public WebSecurityCustomizer configurer() {
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
