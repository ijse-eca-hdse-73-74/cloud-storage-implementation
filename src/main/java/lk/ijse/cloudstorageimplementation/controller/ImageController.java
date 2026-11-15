package lk.ijse.cloudstorageimplementation.controller;

import lk.ijse.cloudstorageimplementation.service.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

/**
 * ========================================================
 * Author  : Shamodha Sahan
 * GitHub  : https://github.com/shamodhas
 * Website : https://shamodha.com
 * ========================================================
 * Date    : 11/16/2026 12:41 AM
 * Project : cloud-storage-implementation
 * ========================================================
 */

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private final StorageService storageService;

    public ImageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> upload(@RequestParam("image") MultipartFile file) {
        try {
            String savedFilename = storageService.upload(file);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("filename", savedFilename));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<String>> listAll() {
        return ResponseEntity.ok(storageService.listAll());
    }

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws IOException {
        try {
            Resource resource = storageService.load(filename);
            String contentType = Files.probeContentType(resource.getFile().toPath());
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType != null ? contentType : "application/octet-stream"))
                    .body(resource);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{filename}")
    public ResponseEntity<Void> delete(@PathVariable String filename) {
        try {
            storageService.delete(filename);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
