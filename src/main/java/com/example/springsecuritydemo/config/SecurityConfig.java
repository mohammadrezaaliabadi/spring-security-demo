package com.example.springsecuritydemo.config;

import com.example.springsecuritydemo.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsersService usersService;
//    @Bean
//    public DataSource dataSource(){
//        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//        dataSourceBuilder.url("jdbc:mysql://localhost:3306/blog");
//        dataSourceBuilder.username("root");
//        dataSourceBuilder.password("admin");
//        return dataSourceBuilder.build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource){
//        String usersByUsernameQuery = "select email,password,enabled from users where email=?";
//        String authoritiesByUsernameQuery = "select email, user_roles from authorities where email=?";
//        var userDetailManger = new JdbcUserDetailsManager(dataSource);
//        userDetailManger.setUsersByUsernameQuery(usersByUsernameQuery);
//        userDetailManger.setAuthoritiesByUsernameQuery(authoritiesByUsernameQuery);
//        return userDetailManger;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").usernameParameter("email");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersService);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
