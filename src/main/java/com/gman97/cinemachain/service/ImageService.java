package com.gman97.cinemachain.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

@Service
public class ImageService {

    @Value("${app.image.bucket}")
    private String bucket;

    public void uploadImage(String imageName, InputStream content) {
        Path fullImagePath = Path.of(bucket, imageName);

        try(content) {
            Files.createDirectories(fullImagePath.getParent());
            Files.write(fullImagePath, content.readAllBytes(), CREATE, TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<byte[]> get(String imageName) {
        Path fullImagePath = Path.of(bucket, imageName);

        try {
            return Files.exists(fullImagePath)
                    ? Optional.of(Files.readAllBytes(fullImagePath))
                    : Optional.empty();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
