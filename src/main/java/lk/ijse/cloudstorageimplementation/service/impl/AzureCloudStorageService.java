package lk.ijse.cloudstorageimplementation.service.impl;

import com.azure.core.util.Context;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.models.BlobStorageException;
import com.azure.storage.blob.options.BlobParallelUploadOptions;
import lk.ijse.cloudstorageimplementation.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * ========================================================
 * Author  : Shamodha Sahan
 * GitHub  : https://github.com/shamodhas
 * Website : https://shamodha.com
 * ========================================================
 * Date    : 11/16/2026 3:02 AM
 * Project : cloud-storage-implementation
 * ========================================================
 */

@Service
@Profile("azure")
public class AzureCloudStorageService implements StorageService {

    @Autowired
    private BlobContainerClient blobContainerClient;

    @Override
    public String upload(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }

        String originalFilename = file.getOriginalFilename();
        String extension = (originalFilename != null && originalFilename.contains("."))
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";

        String savedFilename = UUID.randomUUID() + extension;

        try {
            BlobClient blobClient = blobContainerClient.getBlobClient(savedFilename);
            BlobHttpHeaders headers = new BlobHttpHeaders().setContentType(contentType);

            blobClient.uploadWithResponse(
                    new BlobParallelUploadOptions(file.getInputStream(), file.getSize())
                            .setHeaders(headers),
                    null,
                    Context.NONE
            );
        } catch (IOException | BlobStorageException e) {
            throw new RuntimeException("Failed to store file", e);
        }

        return savedFilename;
    }

    @Override
    public List<String> listAll() {
        try {
            return blobContainerClient.listBlobs().stream()
                    .map(BlobItem::getName)
                    .collect(Collectors.toList());
        } catch (BlobStorageException e) {
            throw new RuntimeException("Failed to list files", e);
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            BlobClient blobClient = blobContainerClient.getBlobClient(filename);

            if (Boolean.FALSE.equals(blobClient.exists())) {
                throw new IllegalArgumentException("File not found: " + filename);
            }

            return new InputStreamResource(blobClient.openInputStream());
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (BlobStorageException e) {
            throw new RuntimeException("Failed to load file", e);
        }
    }

    @Override
    public void delete(String filename) {
        try {
            BlobClient blobClient = blobContainerClient.getBlobClient(filename);
            blobClient.deleteIfExists();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (BlobStorageException e) {
            throw new RuntimeException("Failed to delete file", e);
        }
    }
}