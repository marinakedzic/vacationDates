package com.marina.vacationDates.config;

import com.marina.vacationDates.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

public class SecurityUser implements UserDetails {
    private final User user;

    public SecurityUser(User user) {
        this.user = user;
    }
    @Override
    public String getUsername() {
        return user.getEmail();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String[] userRoles = user.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
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
        return true;}
}
