package com.example.fileuploaddemo.controller;


import com.example.fileuploaddemo.dto.UploadForm;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(
            @ModelAttribute UploadForm form) {

        Map<String, Object> response = new HashMap<>();
        response.put("fileName", form.getFile() != null ? form.getFile().getOriginalFilename() : null);
        response.put("size", form.getFile() != null ? form.getFile().getSize() : 0);
        response.put("owner", form.getOwner());
        response.put("destination", form.getDestination());

        return ResponseEntity.ok(response);
    }
}
