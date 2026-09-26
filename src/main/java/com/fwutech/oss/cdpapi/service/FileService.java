package com.fwutech.oss.cdpapi.service;

import com.fwutech.oss.cdpapi.dto.FileResponse;
import com.fwutech.oss.cdpapi.dto.PageResponse;
import com.fwutech.oss.cdpapi.repository.clickhouse.FileRepository;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public PageResponse<FileResponse> findAll(
            int page,
            int size) {

        validatePagination(page, size);

        return fileRepository.findAll(page, size);
    }

    public FileResponse findById(String fileId) {

        if (fileId == null || fileId.isBlank()) {
            throw new IllegalArgumentException(
                    "fileId must not be blank"
            );
        }

        return fileRepository.findById(fileId);
    }

    private void validatePagination(
            int page,
            int size) {

        if (page < 0) {
            throw new IllegalArgumentException(
                    "page must be greater than or equal to 0"
            );
        }

        if (size <= 0) {
            throw new IllegalArgumentException(
                    "size must be greater than 0"
            );
        }

        if (size > 1000) {
            throw new IllegalArgumentException(
                    "size must not be greater than 1000"
            );
        }
    }
}