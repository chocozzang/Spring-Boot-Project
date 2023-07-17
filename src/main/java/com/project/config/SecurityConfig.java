package com.project.config;

import com.project.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    MemberService memberService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/members/login")        // 로그인 페이지 지정
                .defaultSuccessUrl("/main/")             // 로그인 성공시 이동할 url
                .usernameParameter("email")         // 로그인 시 사용할 파라미터로 email 사용
                .failureUrl("/members/login/error") // 로그인 실패시 이동할 url
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))  // 로그아웃 url
                .logoutSuccessUrl("/main/");             // 로그아웃 성공시 이동할 url

        // permitAll() - 해당 url에 대해 모든 접근 허용
        // .anyRequest().authenticated() - 나머지 요청들에 대해서는 모두 인증을 요구함
        http.authorizeRequests()
                .mvcMatchers("/main/", "/members/**", "/item/**", "/furniture/list", "/board/paging", "/notice/noticeList", "/board/images/*", "/furniture/images/*").permitAll()
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // static 폴더의 하위 파일들은 인증을 무시하도록 설정
       web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/icomoon/**", "/images/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}





