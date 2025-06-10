package Caprish.config;

import Caprish.Model.imp.users.Client;
import Caprish.Model.imp.users.Credential;
import Caprish.Model.imp.users.Staff;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
public class MyUserDetails implements UserDetails {

    private final Credential credential;
    private final Client client; // null si no es cliente
    private final Staff staff;   // null si no es staff
    private final Collection<? extends GrantedAuthority> authorities;

    public MyUserDetails(Credential credential,
                         Client client,
                         Staff staff,
                         Collection<? extends GrantedAuthority> authorities) {
        this.credential  = credential;
        this.client      = client;
        this.staff       = staff;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return credential.getPassword();
    }

    @Override
    public String getUsername() {
        return credential.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // o lógica personalizada
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // o lógica personalizada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // o lógica personalizada
    }

    @Override
    public boolean isEnabled() {
        return credential.isEnabled();
    }
}

