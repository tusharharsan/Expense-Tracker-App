package org.example.Service;

import org.example.Entities.UserRoles;
import org.example.Entities.UsersEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails extends UsersEntity implements UserDetails {
    private String username;

    private String password;


    Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(UsersEntity byusername){
        this.username = byusername.getUsername();
        this.password = byusername.getPassword();

        List<GrantedAuthority> auths = new ArrayList<>();

        for(UserRoles role : byusername.getRoles()){
            auths.add(new SimpleGrantedAuthority(role.getName().toUpperCase()));
        }
        this.authorities = auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
