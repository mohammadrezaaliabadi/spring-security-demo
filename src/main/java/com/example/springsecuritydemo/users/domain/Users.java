package com.example.springsecuritydemo.users.domain;

import com.example.springsecuritydemo.enums.UserRoles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Users implements UserDetails {
    @Id
    @GeneratedValue
    private Long id;

    private String email;
    private String password;

    private Boolean enabled = true;

//    @ElementCollection(targetClass = UserRoles.class,fetch = FetchType.EAGER)
//    @CollectionTable(
//            name = "authorities",
//            joinColumns = @JoinColumn(name = "email",referencedColumnName = "email"))
//    @Enumerated(EnumType.STRING)
//    private List<UserRoles> authorities;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Roles> roles;


    public Users() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var authorities = new ArrayList<GrantedAuthority>();
        for (Roles role:roles){
            authorities.addAll(role.getAuthorities());
        }
        return authorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
        return enabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }
}
