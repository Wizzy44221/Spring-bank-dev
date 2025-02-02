package com.wizzy.Wizzy.Banking.Application.domain.entity;


import com.wizzy.Wizzy.Banking.Application.domain.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users_tbl")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity extends BaseClass implements UserDetails {

    private String firstname;

    private String lastName;

    private String otherName;

    private String address;

    private String stateOfOrigin;

    private String accountNumber;

    private String dob;

    private BigDecimal accountBalance;

    private String phoneNumber;

    private String NIN;

    private String profilePicture;

    private  String status;

    private String password;

    private  String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private  String occupation;

    private String gender;

    private String maritalStatus;





    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
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
        return true;
    }


}
