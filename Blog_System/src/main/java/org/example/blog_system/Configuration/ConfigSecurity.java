package org.example.blog_system.Configuration;

import lombok.RequiredArgsConstructor;
import org.example.blog_system.Service.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class ConfigSecurity {

    private final MyUserDetailsService myUserDetailsService;

    //1:
    //This "dao" method return 'user' and generated password, but we must take them from DB!
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){


        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);

        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoAuthenticationProvider;

    }

    ////////////////////////

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws  Exception{

        httpSecurity.csrf().disable()          //Disable the CSRF attack
                .sessionManagement()  //Only session mgmt, cookies just delete it
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  //Session
                .and()
                .authenticationProvider(daoAuthenticationProvider()) //pass the method
                .authorizeHttpRequests()
                .requestMatchers("/api/v2/user/register", "/api/v2/blog/get/all-blogs", "/api/v2/blog/get/blog-byId/", "/api/v2/blog/get/blog-byTitle/").permitAll()
                .requestMatchers("/api/v2/user/get/users", "/api/v2/user/delete/user/").hasAuthority("ADMIN")
                .requestMatchers("/api/v2/blog/get/user-blogs", "/api/v2/blog/add-blog", "/api/v2/blog/update/blog/", "/api/v2/blog/delete/blog/","api/v2/user/update/user/").hasAuthority("USER")
                .anyRequest().authenticated() //تقفل اي شي نسيته
                .and()
                .logout().logoutUrl("/api/v2/logout") //logout and login already exist in spring
                .deleteCookies("JSESSIONID") //now from Postman Cookie
                .invalidateHttpSession(true)  //delete the session
                .and()
                .httpBasic();
        return httpSecurity.build();

    } //End











}
