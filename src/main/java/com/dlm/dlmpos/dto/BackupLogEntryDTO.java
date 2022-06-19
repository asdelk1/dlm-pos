package com.dlm.dlmpos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BackupLogEntryDTO {

    private Long id;
    private String time;
    private UserDTO user;
    private String fileName;

}
