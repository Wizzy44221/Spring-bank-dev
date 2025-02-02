package com.wizzy.Wizzy.Banking.Application.service;


import com.wizzy.Wizzy.Banking.Application.domain.entity.UserEntity;
import com.wizzy.Wizzy.Banking.Application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService  implements UserDetailsService {

    private  final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //TERNARY OPERATOR
        Optional<UserEntity> userEntityOptional = isEmail(username)
                ? userRepository.findByEmail(username)
                : userRepository.findByPhoneNumber(normalizedPhoneNumber(username));


       // return userRepository.findByEmail(username).orElseThrow(()-> new  UsernameNotFoundException("User not found"));

        return userEntityOptional.get();
    }

    private String normalizedPhoneNumber(String phoneNumber) {

        return phoneNumber.replaceAll("[^0-9]", "");

    }

    private boolean isEmail(String input) {

        return input.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}
