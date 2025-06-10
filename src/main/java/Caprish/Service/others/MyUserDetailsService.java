package Caprish.Service.others;

import Caprish.Repository.interfaces.users.ClientRepository;
import Caprish.Repository.interfaces.users.CredentialRepository;
import Caprish.Repository.interfaces.users.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final JdbcUserDetailsManager jdbcManager;
    private final CredentialRepository credentialRepository;
    private final ClientRepository clientRepository;
    private final StaffRepository staffRepository;

    @Autowired
    public MyUserDetailsService(CredentialRepository credentialRepository,
                                ClientRepository clientRepository,
                                StaffRepository staffRepository,
                                JdbcUserDetailsManager jdbcManager) {
        this.credentialRepository = credentialRepository;
        this.clientRepository = clientRepository;
        this.staffRepository = staffRepository;
        this.jdbcManager = jdbcManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return jdbcManager.loadUserByUsername(username);
    }

}
