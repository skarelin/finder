package com.business.finder.security;

import com.business.finder.user.db.BfUserRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
@AllArgsConstructor
class BfSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_GET_WHITELIST = {
            "/version/**",
            "/v2/api-docs/**",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };

    private static final String[] AUTH_POST_WHITELIST = {
            "/register/**",
            "/login/**"
    };

    private final BfUserRepository bfUserRepository;

    @Bean
    User systemUser() {
        return new User("bfSystemUser1", "", List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // TODO. Should we disable it? Google it.
        http
                .authorizeRequests()
                .mvcMatchers(HttpMethod.POST, AUTH_POST_WHITELIST).permitAll()
                .mvcMatchers(HttpMethod.GET, AUTH_GET_WHITELIST).permitAll()
                .anyRequest().authenticated()
        .and()
                .httpBasic() // TODO. Not sure if I need this.
        .and()
                .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @SneakyThrows
    private JsonUsernameAuthenticationFilter authenticationFilter() {
        JsonUsernameAuthenticationFilter filter = new JsonUsernameAuthenticationFilter();
        filter.setAuthenticationManager(super.authenticationManager());
        return filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        BfUserDetailsService bfUserDetailsService = new BfUserDetailsService(bfUserRepository);
        provider.setUserDetailsService(bfUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


// Difference between antMatcher and mvcMatcher
//    Generally mvcMatcher is more secure than an antMatcher. As an example:
//
//        antMatchers("/secured") matches only the exact /secured URL
//        mvcMatchers("/secured") matches /secured as well as /secured/, /secured.html, /secured.xyz
//        and therefore is more general and can also handle some possible configuration mistakes.
// Source: https://stackoverflow.com/questions/50536292/difference-between-antmatcher-and-mvcmatcher