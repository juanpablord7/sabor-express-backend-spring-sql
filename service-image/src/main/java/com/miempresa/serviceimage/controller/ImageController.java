package com.miempresa.serviceimage.controller;

import com.miempresa.apigateway.dto.Image;
import com.miempresa.apigateway.service.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/**")
    public ResponseEntity<byte[]> getImage(HttpServletRequest request) throws IOException {
        String path = extractPath(request);
        Image image = imageService.getImage(path);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getContentType()))
                .body(image.getContent());
    }

    @PostMapping("/{folder}/{filename}")
    public ResponseEntity<String> postImage(
            @RequestParam("file") MultipartFile file,
            @PathVariable String folder,
            @PathVariable String filename
    ) {
        imageService.saveImage(folder + "/" + filename, file);
        return ResponseEntity.ok("Image uploaded successfully");
    }

    @PutMapping(value = "/{folder}/{filename}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> putImage(
            @RequestParam("file") MultipartFile file,
            @PathVariable String folder,
            @PathVariable String filename
    ) {
        imageService.replaceImage(folder + "/" + filename, file);
        return ResponseEntity.ok("Image replaced successfully");
    }

    @PatchMapping("/**")
    public ResponseEntity<String> patchImage(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request
    ) {
        String path = extractPath(request);
        imageService.replaceImage(path, file);
        return ResponseEntity.ok("Image patched successfully");
    }

    @DeleteMapping("/**")
    public ResponseEntity<String> deleteImage(HttpServletRequest request) {
        String path = extractPath(request);
        imageService.deleteImage(path);
        return ResponseEntity.ok("Image deleted successfully");
    }

    private String extractPath(HttpServletRequest request) {
        String fullPath = request.getRequestURI();
        return fullPath.replaceFirst("^/api/image/?", "");
    }
}
