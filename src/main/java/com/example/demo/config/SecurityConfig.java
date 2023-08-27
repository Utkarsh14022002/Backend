package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.filter.JwtTokenFilter;
import com.example.demo.service.UserDetailService;
import com.example.demo.util.JwtTokenUtil;


@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {



	private JwtTokenUtil jwtTokenUtil;

	private UserDetailService userDetailsService;

	
	@Autowired
	public SecurityConfig(JwtTokenUtil jwtTokenUtil,UserDetailService userDetailsService) {
		this.jwtTokenUtil=jwtTokenUtil;
		this.userDetailsService=userDetailsService;
	}

	@Bean
	public JwtTokenFilter authenticationJwtTokenFilter() {
		return new JwtTokenFilter(jwtTokenUtil,userDetailsService);
	}

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
             .cors().and()
            .csrf().disable()
            .authorizeRequests()
         
                .antMatchers("/logins/**").permitAll()
                .antMatchers("/accounts/**").authenticated()
                .antMatchers("/payees/**").authenticated() 
                .antMatchers("/transactions/**").authenticated()
                .anyRequest().authenticated()
                .and().addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}



