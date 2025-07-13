package com.miempresa.serviceimage.service;

import com.miempresa.serviceimage.dto.Image;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ImageService {

    private static final List<String> ALLOWED_EXTENSIONS = List.of("jpg", "jpeg", "png", "webp");
    private static final String IMAGE_BASE_PATH = System.getProperty("user.dir") + "/images";

    private void validatePath(String path) {
        if (path.contains("..")) {
            throw new IllegalArgumentException("Invalid path: path traversal detected");
        }
    }

    private String getExtension(Path path) {
        String filename = path.getFileName().toString();
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex >= 0 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }

    public Image getImage(String path) {
        validatePath(path);

        Path imagePath = Paths.get(IMAGE_BASE_PATH, path).normalize();

        System.out.println(imagePath);

        if (!Files.exists(imagePath)) {
            throw new IllegalArgumentException("The file doesn't exist");
        }

        String extension = getExtension(imagePath);
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException("Invalid file extension: " + extension);
        }

        try {
            String contentType = Files.probeContentType(imagePath);
            byte[] content = Files.readAllBytes(imagePath);
            return new Image(contentType, content);
        } catch (IOException e) {
            throw new RuntimeException("Error reading image file", e);
        }
    }

    public void saveImage(String path, MultipartFile file) {
        validatePath(path);

        System.out.println(path);

        if (file.isEmpty()) {
            throw new IllegalArgumentException("Uploaded file is empty");
        }

        String extension = getExtension(Paths.get(path));
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException("Unsupported file extension: " + extension);
        }

        Path destination = Paths.get(IMAGE_BASE_PATH, path).normalize();

        try {
            System.out.println("Destino absoluto: " + destination.toAbsolutePath());
            System.out.println("Directorio padre: " + destination.getParent());

            Files.createDirectories(destination.getParent());
            file.transferTo(destination.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Error saving image file", e);
        }
    }

    public void deleteImage(String path) {
        validatePath(path);

        Path imagePath = Paths.get(IMAGE_BASE_PATH, path).normalize();

        try {
            if (!Files.exists(imagePath)) {
                throw new IllegalArgumentException("The file does not exist");
            }
            Files.delete(imagePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete the file", e);
        }
    }
}
