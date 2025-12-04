package com.example.fileuploaddemo.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadForm {
    private MultipartFile file;
    private String owner="system";
    private String destination;
}
