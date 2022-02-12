package com.devendrabrain.OrgMgr.dao;


import com.devendrabrain.OrgMgr.configbean.CustomUserDetails;
import com.devendrabrain.OrgMgr.entity.User;
import com.devendrabrain.OrgMgr.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.stream.Collectors;

@Repository
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if(user == null) {

            throw new UsernameNotFoundException("User does not exists with :"+username);
        }

        Collection<GrantedAuthority> grantedAuthorities = user.getRoles().stream().map(role -> {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role.getRolename());
            return simpleGrantedAuthority;
        }).collect(Collectors.toList());

        return new CustomUserDetails(user.getUsername(),user.getPassword(),user.isEnabled(),grantedAuthorities);
    }
}
