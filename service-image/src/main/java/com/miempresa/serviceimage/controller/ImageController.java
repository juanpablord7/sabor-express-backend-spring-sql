package com.miempresa.serviceimage.controller;

import com.miempresa.serviceimage.dto.Image;
import com.miempresa.serviceimage.service.ImageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    private String extractPath(HttpServletRequest request) {
        String fullPath = request.getRequestURI();
        return fullPath.replaceFirst("^/image/?", "");
    }

    @GetMapping("/**")
    public ResponseEntity<byte[]> getImage(HttpServletRequest request) throws IOException {
        String path = extracutPath(request);
        Image image = imageService.getImage(path);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getContentType()))
                .body(image.getContent());
    }

    @PostMapping(value = "/**", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> postImage(
            HttpServletRequest request,
            @RequestParam("file") MultipartFile file
    ) {
        String path = extractPath(request);
        imageService.saveImage(path, file);
        return ResponseEntity.ok("Image uploaded successfully");
    }

    @PutMapping(value = "/**", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> putImage(
            HttpServletRequest request,
            @RequestParam("file") MultipartFile file
    ) {
        String path = extractPath(request);
        imageService.saveImage(path, file);
        return ResponseEntity.ok("Image replaced successfully");
    }

    @PatchMapping("/**")
    public ResponseEntity<String> patchImage(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request
    ) {
        String path = extractPath(request);
        imageService.saveImage(path, file);
        return ResponseEntity.ok("Image patched successfully");
    }

    @DeleteMapping("/**")
    public ResponseEntity<String> deleteImage(HttpServletRequest request) {
        String path = extractPath(request);
        imageService.deleteImage(path);
        return ResponseEntity.ok("Image deleted successfully");
    }


}
