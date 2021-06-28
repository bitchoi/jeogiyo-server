package com.bitchoi.jeogiyoserver.security;

import com.bitchoi.jeogiyoserver.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
    private long userId;

    private String email;

    private String username;

    private String password;

    private boolean isAccountNonExpired;

    private boolean isAccountNonLocked;

    private boolean isCredentialsNonExpired;

    private boolean isEnabled;


    private Collection<? extends GrantedAuthority> authorities;

    public UserDetails(User user) {
        this.email = user.getEmail();
        this.userId = user.getUserId();
        this.isEnabled = true;
        this.isAccountNonLocked = true;
        this.isCredentialsNonExpired = true;
        this.isAccountNonExpired = true;
    }
}
