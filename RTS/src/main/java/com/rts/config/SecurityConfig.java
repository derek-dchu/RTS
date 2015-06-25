package com.rts.config;

import com.rts.security.PostSuccessfulAuthenticationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String failureUrl = "/index.html?login_error=1";
    private static final String logoutSuccessUrl = "/";
    private static final String rememberMeKey = "remMeKey";
    private static final int tokenValiditySeconds = 900;
    private static final String filterProccessUrl = "/j_spring_security_check";

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, UserDetailsService customUserDetailsService) throws Exception {
        auth
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/admin.html").hasRole("ADMIN")
                    .antMatchers("/user_transaction.html").hasRole("USER")
                    .antMatchers("/index.html").permitAll()
                .and()
                .formLogin()
                    .loginPage("/index.html")
                    .successHandler(getAuthSuxHandler())
                    .failureUrl(failureUrl)
                .and()
                .logout()
                    .invalidateHttpSession(true)
                    .logoutSuccessUrl(logoutSuccessUrl)
                    .deleteCookies("JSESSIONID")
                .and()
                .rememberMe()
                    .key(rememberMeKey)
                    .tokenValiditySeconds(tokenValiditySeconds)
                .and()
                .addFilter(getFilter());
        http.csrf().disable();

    }

    private AuthenticationSuccessHandler getAuthSuxHandler() {
        return new PostSuccessfulAuthenticationHandler();
    }

    private PasswordEncoder getPasswordEncoder() {
        return new Md5PasswordEncoder();
    }

    private UsernamePasswordAuthenticationFilter getFilter() throws Exception {
        UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(super.authenticationManagerBean());
        filter.setFilterProcessesUrl(filterProccessUrl);
        return filter;
    }
}
