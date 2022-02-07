package com.dlm.dlmpos.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BackupLogEntryDTO {

    private Long id;
    private LocalDateTime time;
    private UserDTO user;
    private String fileName;

}
