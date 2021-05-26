package com.dlm.dlmpos.service;

import com.dlm.dlmpos.entity.User;
import com.dlm.dlmpos.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

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

    public void deleteUser(long id){
        User user = this.userRepository.findById(id).get();
        this.userRepository.delete(user);
    }
}
