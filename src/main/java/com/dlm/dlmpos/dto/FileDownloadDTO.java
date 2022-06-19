package com.dlm.dlmpos.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
public class FileDownloadDTO {

    private String fileName;
    private File file;
}
