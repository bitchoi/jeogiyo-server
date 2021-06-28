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

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    // @formatter:off
    public static final String[] permitAll = {
             "/swagger-ui.html"
            , "/auth/**"
            , "/registrations/**"
            , "/error"
    };
    public static final String[] API_DOCS_PATHS = {
            "/v2/api-docs"
            , "/swagger-resources"
            , "/swagger-resources/**"
            , "/swagger-ui.html"
            , "/webjars/**"
            , "/csrf"
    };
    // @formatter:on
    private final AuthenticationProvider provider;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(provider);
    }

    @Bean
    AuthenticationFilter authenticationFilter() throws Exception {
        RequestMatcher rm =
                new NegatedRequestMatcher(new OrRequestMatcher(Arrays.stream(permitAll).map(AntPathRequestMatcher::new).collect(Collectors.toList())));
        final var filter = new AuthenticationFilter(rm);
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().httpBasic().disable()
                .authenticationProvider(provider)
                .addFilterAfter(authenticationFilter(), AnonymousAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(permitAll).permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.headers().frameOptions().disable();
    }
}
