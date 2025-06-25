package Caprish.Service.others;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final JdbcUserDetailsManager jdbcManager;

    @Autowired
    public MyUserDetailsService(JdbcUserDetailsManager jdbcManager) {
        this.jdbcManager = jdbcManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return jdbcManager.loadUserByUsername(username);
    }
}