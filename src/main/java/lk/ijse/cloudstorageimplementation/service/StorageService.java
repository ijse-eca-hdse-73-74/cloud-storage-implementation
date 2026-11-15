package lk.ijse.cloudstorageimplementation.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

public interface StorageService {
    String upload(MultipartFile file);
    List<String> listAll();
    Resource load(String filename);
    void delete(String filename);
}
