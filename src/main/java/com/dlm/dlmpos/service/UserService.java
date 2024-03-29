package com.dlm.dlmpos.service;

import com.dlm.dlmpos.entity.User;
import com.dlm.dlmpos.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    public final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isUserExists(String username) {
        return this.userRepository.findByUsername(username).isPresent();
    }

    public User saveUser(User user){

        return this.userRepository.save(user);
    }

    public List<User> listUser(){
        return this.userRepository.findAll();
    }

    public Optional<User> getUser(String username){
        return this.userRepository.findByUsername(username);
    }

    public Optional<User> getUser(Long id){
        return  this.userRepository.findById(id);
    }

    public void deleteUser(long id){
        User user = this.userRepository.findById(id).get();
        this.userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Optional<User> user = this.userRepository.findByUsername(s);
        if(user.isEmpty()){
            return null;
        }

        String password = "{noop}"+user.get().getPassword();

        return org.springframework.security.core.userdetails.User.withUsername(user.get().getUsername())
                .password(password)
                .roles("")
                .build();
    }
}
