package com.dlm.dlmpos.controller;

import com.dlm.dlmpos.dto.CredentialsDTO;
import com.dlm.dlmpos.dto.UserDTO;
import com.dlm.dlmpos.entity.User;
import com.dlm.dlmpos.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AdminController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder encoder;

    public AdminController(UserService userService,
                           ModelMapper modelMapper,
                           PasswordEncoder encoder) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.encoder = encoder;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUser() {

        List<User> list = this.userService.listUser();
        List<UserDTO> dtoList = list.stream().map(u -> this.modelMapper.map(u, UserDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping("/login")
    public ResponseEntity<User> logIn(@Valid @RequestBody CredentialsDTO credential) {

        // for now check the db for given user name, if there is a matting user name then return true.
        return ResponseEntity.ok(new User());
    }

    @PostMapping("/save-user")
    public ResponseEntity<URI> saveUser(@Valid @RequestBody UserDTO dto) {

        if (this.userService.isUserExists(dto.getUsername())) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(this.encoder.encode(dto.getPassword()));
        user.setAdmin(dto.isAdmin());

        User createdUser = this.userService.saveUser(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostConstruct
    private void addDefaultUser() {
        if (!this.userService.isUserExists("Admin")) {
            User user = new User();
            user.setUsername("Admin");
            user.setPassword(this.encoder.encode("Admin"));
            user.setAdmin(true);

            this.userService.saveUser(user);
        }
    }


}
