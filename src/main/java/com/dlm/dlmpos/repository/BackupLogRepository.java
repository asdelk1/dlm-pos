package com.dlm.dlmpos.repository;

import com.dlm.dlmpos.entity.BackupLogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BackupLogRepository extends JpaRepository<BackupLogEntry, Long> {

}
