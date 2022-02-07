package com.dlm.dlmpos.controller;

import com.dlm.dlmpos.dto.BackupLogEntryDTO;
import com.dlm.dlmpos.dto.CredentialsDTO;
import com.dlm.dlmpos.dto.FileDownloadDTO;
import com.dlm.dlmpos.dto.UserDTO;
import com.dlm.dlmpos.entity.BackupLogEntry;
import com.dlm.dlmpos.entity.User;
import com.dlm.dlmpos.service.BackupService;
import com.dlm.dlmpos.service.UserService;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.URI;
import java.net.URLConnection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AdminController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final BackupService backupService;


    public AdminController(UserService userService,
                           ModelMapper modelMapper,
                           BackupService backupService) {
        this.userService = userService;
        this.modelMapper = modelMapper;

        this.backupService = backupService;
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
        if (user.isPresent() && user.get().getPassword().equals(credential.getPassword())) {
            UserDTO dto = new UserDTO();
            dto.setId(user.get().getId());
            dto.setUsername(user.get().getUsername());
            dto.setAdmin(user.get().isAdmin());

            return ResponseEntity.ok(dto);
        } else {
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
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
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

    @GetMapping("/database/backups")
    public ResponseEntity<List<BackupLogEntryDTO>> getEntries() {
        List<BackupLogEntry> list = this.backupService.getLogEntries();
        List<BackupLogEntryDTO> dtoList = list.stream().map(e -> this.modelMapper.map(e, BackupLogEntryDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

//    @PostMapping("/database/backups")
//    @ResponseBody
//    public void backupDatabase(HttpServletResponse response) {
//        String msg;
//        HttpStatus status;
//        try {
//            FileDownloadDTO fileDownload = this.backupService.backupDatabase();
//            msg = "SUCCESS";
//            status = HttpStatus.CREATED;
//            if (fileDownload != null) {
//                String mimeType = URLConnection.guessContentTypeFromName(fileDownload.getFile().getName());
//                if (mimeType == null) {
//                    mimeType = "application/octet-stream";
//                }
//                response.setStatus(HttpStatus.CREATED.value());
//                response.setContentType(mimeType);
//                response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", fileDownload.getFile().getName()));
//                response.setContentLength((int) fileDownload.getFile().length());
//                InputStream inputStream = new BufferedInputStream(new FileInputStream(fileDownload.getFile()));
//                FileCopyUtils.copy(inputStream, response.getOutputStream());
//            }
//        } catch (Exception e) {
//            msg = e.getMessage();
//            status = HttpStatus.INTERNAL_SERVER_ERROR;
//        }
//    }

    @PostMapping("/database/backups")
    public ResponseEntity<Resource> getDatabaseBackup() throws Exception {
        FileDownloadDTO dto = this.backupService.backupDatabase();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,  String.format("attachment; filename=\"%s\"", dto.getFile().getName()));
//        String mediaTypeStr = URLConnection.guessContentTypeFromName(dto.getFile().getName());
//        if(mediaTypeStr == null){
//            mediaTypeStr = MediaType.APPLICATION_OCTET_STREAM_VALUE;
//        }

        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;

        InputStreamResource resource = new InputStreamResource(new FileInputStream(dto.getFile()));
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(dto.getFile().length())
                .contentType(mediaType)
                .body(resource);
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
