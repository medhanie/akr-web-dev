package io.medhanie.akrweb.be.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@Order(101)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/", "/forgotpassword", "/resetpassword").permitAll()
                .antMatchers("/admin", "/admin/**").hasRole("ADMIN").antMatchers("/user", "/user/**").hasRole("USER")
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/").failureUrl("/login?error").permitAll()
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll().and()
                .exceptionHandling().accessDeniedPage("/notfound");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/webjars/**", "/js/**", "/img/**");
    }
}