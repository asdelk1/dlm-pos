package com.dlm.dlmpos.controller;

import com.dlm.dlmpos.dto.CredentialsDTO;
import com.dlm.dlmpos.dto.UserDTO;
import com.dlm.dlmpos.entity.User;
import com.dlm.dlmpos.service.UserService;
import org.bouncycastle.util.encoders.Base64;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AdminController {

    private final UserService userService;
    private final ModelMapper modelMapper;


    public AdminController(UserService userService,
                           ModelMapper modelMapper
    ) {
        this.userService = userService;
        this.modelMapper = modelMapper;

    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUser() {

        List<User> list = this.userService.listUser();
        List<UserDTO> dtoList = list.stream().map(u -> this.modelMapper.map(u, UserDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> logIn(@RequestBody CredentialsDTO credential) {

        // for now check the db for given user name, if there is a matting user name then return true.
        Optional<User> user = this.userService.getUser(credential.getUsername());
        if(user.isPresent() && user.get().getPassword().equals(credential.getPassword())){
            UserDTO dto = new UserDTO();
            dto.setId(user.get().getId());
            dto.setUsername(user.get().getUsername());
            dto.setAdmin(user.get().isAdmin());

            return ResponseEntity.ok(dto);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/save-user")
    public ResponseEntity<URI> saveUser(@Valid @RequestBody UserDTO dto) {

        if (this.userService.isUserExists(dto.getUsername())) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setAdmin(dto.isAdmin());

        User createdUser = this.userService.saveUser(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id){
        this.userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {

        Optional<User> user = this.userService.getUser(username);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user.get());
    }

    @PostConstruct
    private void addDefaultUser() {
        if (!this.userService.isUserExists("admin")) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword("YWRtaW4=");
            user.setAdmin(true);

            this.userService.saveUser(user);
        }
    }


}
