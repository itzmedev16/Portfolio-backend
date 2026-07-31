package com.portfolio.service;

import com.portfolio.exception.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class FileUploadService {

    private final SupabaseStorageService supabaseStorageService;

    public FileUploadService(SupabaseStorageService supabaseStorageService) {
        this.supabaseStorageService = supabaseStorageService;
    }

    // Upload an image
    public String uploadImage(MultipartFile file) {
        validateImageFile(file);
        return storeFile(file, "images");
    }

    // Upload a PDF
    public String uploadPdf(MultipartFile file) {
        validatePdfFile(file);
        return storeFile(file, "resume");
    }

    // Store file in Supabase
    private String storeFile(MultipartFile file, String folder) {

        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {

            if (originalFileName.contains("..")) {
                throw new FileUploadException(
                        "Filename contains invalid path sequence " + originalFileName);
            }

            String extension = "";

            int dot = originalFileName.lastIndexOf('.');

            if (dot > 0) {
                extension = originalFileName.substring(dot);
            }

            String newFileName = UUID.randomUUID() + extension;

            return supabaseStorageService.upload(
                    file.getBytes(),
                    newFileName,
                    file.getContentType(),
                    folder
            );

        } catch (IOException e) {
            throw new FileUploadException("Could not upload file.", e);
        }
    }

    // Delete (we'll implement this later)
    public void deleteFileByUrl(String fileUrl) {
        // TODO
    }

    private void validateImageFile(MultipartFile file) {

        if (file.isEmpty()) {
            throw new FileUploadException("Cannot upload empty file");
        }

        String contentType = file.getContentType();

        if (contentType == null) {
            throw new FileUploadException("Content type is undefined");
        }

        List<String> allowed = Arrays.asList(
                "image/jpeg",
                "image/jpg",
                "image/png",
                "image/gif",
                "image/webp",
                "image/pjpeg",
                "image/x-png"
        );

        if (!allowed.contains(contentType.toLowerCase())) {
            throw new FileUploadException(
                    "Only JPG, JPEG, PNG, GIF and WEBP are allowed");
        }
    }

    private void validatePdfFile(MultipartFile file) {

        if (file.isEmpty()) {
            throw new FileUploadException("Cannot upload empty file");
        }

        String contentType = file.getContentType();

        if (contentType == null ||
                !contentType.equalsIgnoreCase("application/pdf")) {

            throw new FileUploadException(
                    "Only PDF documents are allowed");
        }
    }
}