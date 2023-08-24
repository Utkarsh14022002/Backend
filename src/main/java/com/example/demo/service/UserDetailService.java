package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.repository.LoginRepository;
import com.example.demo.model.Login;

@Service
@Component
public class UserDetailService implements UserDetailsService {


    @Autowired
    private LoginRepository loginRepository; // Replace with your repository


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Login> userOptional = loginRepository.findByUserid(username);
        if (userOptional.isPresent()) {
            Login user = userOptional.get();
            return new User(user.getUserid(), user.getPassword(), getAuthorities(user));
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }


    private Collection<? extends GrantedAuthority> getAuthorities(Login user) {
    	List<GrantedAuthority> authorities = new ArrayList<>();
    	authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
    	if(user.getAdmin()==1) {
    		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    	}
        return authorities;
    }
}
