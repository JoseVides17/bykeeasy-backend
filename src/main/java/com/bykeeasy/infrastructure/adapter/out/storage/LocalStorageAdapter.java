package com.bykeeasy.infrastructure.adapter.out.storage;

import com.bykeeasy.application.port.out.FileStoragePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class LocalStorageAdapter implements FileStoragePort {

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Override
    public String store(InputStream file, String originalFileName, String folder) {
        try {
            String extension = "";
            int i = originalFileName.lastIndexOf('.');
            if (i > 0) {
                extension = originalFileName.substring(i);
            }
            
            String fileName = UUID.randomUUID().toString() + extension;
            Path root = Paths.get(uploadDir, folder);
            
            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }
            
            Files.copy(file, root.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            
            // Return public path (proxy needed or static resource mapping)
            return "/uploads/" + folder + "/" + fileName;
        } catch (Exception e) {
            throw new RuntimeException("Could not store file", e);
        }
    }
}
