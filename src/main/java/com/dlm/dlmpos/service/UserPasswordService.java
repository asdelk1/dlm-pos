package com.dlm.dlmpos.service;

import com.dlm.dlmpos.entity.User;
import com.dlm.dlmpos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPasswordService implements UserDetailsPasswordService {

    private final UserRepository userRepository;

    public UserPasswordService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails updatePassword(UserDetails userDetails, String s) {

        Optional<User> user = this.userRepository.findByUsername(userDetails.getUsername());
        if(user.isEmpty()){
            return null;
        }
        user.get().setPassword(s);
        return org.springframework.security.core.userdetails.User.withUsername(user.get().getUsername())
                .roles("")
                .password(s)
                .build();
    }
}
