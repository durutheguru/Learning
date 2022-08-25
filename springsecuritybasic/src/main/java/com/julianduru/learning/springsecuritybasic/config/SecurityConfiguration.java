package com.julianduru.learning.springsecuritybasic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.util.Map;

/**
 * created by julian on 04/06/2022
 */
@Configuration
public class SecurityConfiguration {



    @Bean
    public UserDetailsService authenticationManager(DataSource dataSource, PasswordEncoder passwordEncoder) throws Exception {
        var manager = new JdbcUserDetailsManager(dataSource);

        manager.createUser(
            User.builder()
                .username("userrr")
                .password("pwd")
                .authorities("ADMIN")
                .passwordEncoder(passwordEncoder::encode)
                .build()
        );

        return manager;
    }


    @Bean
    public DelegatingPasswordEncoder passwordEncoder() {
        var idToEncode = "bcrypt";
        return new DelegatingPasswordEncoder(
            idToEncode,

            Map.of(
                idToEncode, new BCryptPasswordEncoder(),
                "noop", NoOpPasswordEncoder.getInstance(),
                "pbkdf2", new Pbkdf2PasswordEncoder(),
                "scrypt", new SCryptPasswordEncoder(),
                "sha256", new StandardPasswordEncoder()
            )
        );
    }


    /**
     * /myAccount - Secured
     * /myBalance - Secured
     * /myLoans - Secured
     * /myCards - Secured
     * /notices - Not Secured
     * /contact - Not Secured
     */
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        return http.authorizeRequests()
            .antMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
            .antMatchers("/notices", "/contact").permitAll()
            .and()
            .formLogin()
            .and()
            .httpBasic()
            .and()
            .build();

    }


//    /**
//     * deny all requests
//     * @param http
//     * @return
//     * @throws Exception
//     */
//    @Bean
//    public SecurityFilterChain denyAllSecurityFilterChain(HttpSecurity http) throws Exception {
//
//        return http.authorizeRequests()
//            .anyRequest().denyAll()
//            .and()
//            .formLogin()
//            .and()
//            .httpBasic()
//            .and()
//            .build();
//
//    }


//    /**
//     * permit all requests
//     * @param http
//     * @return
//     * @throws Exception
//     */
//    @Bean
//    public SecurityFilterChain permitAllSecurityFilterChain(HttpSecurity http) throws Exception {
//
//        return http.authorizeRequests()
//            .anyRequest().permitAll()
//            .and()
//            .formLogin()
//            .and()
//            .httpBasic()
//            .and()
//            .build();
//
//    }


}
