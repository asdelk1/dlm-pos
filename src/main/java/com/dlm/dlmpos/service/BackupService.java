package com.dlm.dlmpos.service;

import com.dlm.dlmpos.dto.FileDownloadDTO;
import com.dlm.dlmpos.entity.BackupLogEntry;
import com.dlm.dlmpos.entity.User;
import com.dlm.dlmpos.repository.BackupLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class BackupService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final BackupLogRepository repository;
    private final UserService userService;

    public BackupService(BackupLogRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public FileDownloadDTO backupDatabase() throws Exception {
        String currentTimeStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        String fileName = "d:\\dms-backup_" + currentTimeStr + ".sql";
        File sqlFile;

        String cmd = "mysqldump --user=\"appdev\" --password=\"appdev\" dlm > " + fileName;
        try {
            Process process = Runtime.getRuntime().exec(String.format("cmd.exe /c %s", cmd));
            int processComplete = process.waitFor();
            this.logger.info(String.format("DB backing up process(id=%d, result=%d)", process.pid(), processComplete));

            if (processComplete == 0) {
                // read the file and return the stream to controller
                this.logBackupAction(fileName);
                sqlFile = new File(fileName);
            } else {
                throw new Exception();
            }

            FileDownloadDTO dto = new FileDownloadDTO();
            dto.setFile(sqlFile);
            dto.setFileName(fileName);
            return dto;
        } catch (Exception e) {
            this.logger.error(e.getMessage());
            throw e;
        }
    }

    public List<BackupLogEntry> getLogEntries() {
        return this.repository.findAll(PageRequest.of(0, 30, Sort.by(Sort.Direction.DESC, "id"))).getContent();
    }

    private void logBackupAction(String fileName) {
        UserDetails loggedInUserPrinciple = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = this.userService.getUser(loggedInUserPrinciple.getUsername());
        if (user.isPresent()) {
            BackupLogEntry logEntry = new BackupLogEntry(user.get(), fileName);
            this.repository.save(logEntry);
        }
    }
}
