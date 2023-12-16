package com.example.FileCompressor.service;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class FileCompressionService {

   public byte[] compressFile(MultipartFile file) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
             InputStream inputStream = file.getInputStream()) {

            ZipEntry zipEntry = new ZipEntry(file.getOriginalFilename());
            zipOutputStream.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) > 0) {
                zipOutputStream.write(buffer, 0, len);
            }

            zipOutputStream.closeEntry();
            zipOutputStream.finish();

            return byteArrayOutputStream.toByteArray();
        }
    }
}
