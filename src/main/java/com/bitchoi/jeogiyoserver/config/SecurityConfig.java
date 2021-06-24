package com.bitchoi.jeogiyoserver.config;

import com.bitchoi.jeogiyoserver.security.AuthenticationFilter;
import com.bitchoi.jeogiyoserver.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationProvider authenticationProvider;

    private final JwtUtils jwtUtils;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth){
        auth.authenticationProvider(authenticationProvider);
    }

    @Bean
    AuthenticationFilter authenticationFilter() throws  Exception {
        RequestMatcher rm = new NegatedRequestMatcher(new OrRequestMatcher(new AntPathRequestMatcher("/home")));
        final var filter = new AuthenticationFilter(rm, jwtUtils);
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .authenticationProvider(authenticationProvider)
                .addFilterAfter(authenticationFilter(), AnonymousAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers().permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

}
