package com.example.FileCompressor.controller;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.FileCompressor.service.FileCompressionService;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileCompressionService compressionService;

    public FileController(FileCompressionService compressionService) {
        this.compressionService = compressionService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            byte[] compressedFile = compressionService.compressFile(file);
            String compressedFilename = "compressed_" + file.getOriginalFilename() + ".zip";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDispositionFormData("attachment", compressedFilename);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(compressedFile);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error compressing file: " + e.getMessage());
        }
    }
}
