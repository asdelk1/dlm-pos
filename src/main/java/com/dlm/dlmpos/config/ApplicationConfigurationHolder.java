package com.dlm.dlmpos.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("dlm")
@Getter
@Setter
public class ApplicationConfigurationHolder {

    private String databaseBackupLocation;
}
