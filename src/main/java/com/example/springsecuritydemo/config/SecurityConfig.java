package com.example.springsecuritydemo.config;

import com.example.springsecuritydemo.users.jwt.JwtFilter;
import com.example.springsecuritydemo.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsersService usersService;
    @Autowired
    private OAuth2UserService oAuth2UserService;
    @Autowired
    private JwtFilter jwtFilter;
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
                .antMatchers("/", "/login", "/error", "/info","/jwt/login").permitAll()
//                .antMatchers("/user/**").hasAnyAuthority("USER","ADMIN")
//                .antMatchers("/admin/**").hasAnyAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").usernameParameter("email").successHandler(new LoginSuccessHandler())
                .and().oauth2Login()
                .loginPage("/oauth2Login")
                .authorizationEndpoint().baseUri("/login/oauth2").and()
                .redirectionEndpoint().baseUri("/login/callback").and()
                .userInfoEndpoint().userService(oAuth2UserService).and()
                .successHandler(new LoginSuccessHandler())
                .and().rememberMe().rememberMeCookieName("remember")
                .tokenValiditySeconds(60)
                .rememberMeParameter("remember")
                .and().exceptionHandling().accessDeniedPage("/error")
                .and().logout().logoutUrl("/mylogout").deleteCookies("remember")
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
