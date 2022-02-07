package com.dlm.dlmpos.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "backup_log")
@Getter
@Setter
public class BackupLogEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime time;
    @OneToOne
    private User user;
    private String fileName;

    public BackupLogEntry(){}

    public BackupLogEntry(User user, String fileName) {
        this.time = LocalDateTime.now();
        this.fileName = fileName;
        this.user = user;
    }
}
