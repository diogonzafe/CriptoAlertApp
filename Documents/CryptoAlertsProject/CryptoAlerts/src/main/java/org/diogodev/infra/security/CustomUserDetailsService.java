package org.diogodev.infra.security;

import org.diogodev.Model.Users;
import org.diogodev.Services.UsersFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UsersFacade usersFacade;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = this.usersFacade.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
