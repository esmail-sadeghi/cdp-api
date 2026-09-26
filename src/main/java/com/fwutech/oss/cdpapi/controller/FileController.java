package com.fwutech.oss.cdpapi.controller;

import com.fwutech.oss.cdpapi.dto.FileResponse;
import com.fwutech.oss.cdpapi.dto.PageResponse;
import com.fwutech.oss.cdpapi.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * Get paginated list of files from ClickHouse.
     *
     * Example:
     * GET /api/files?page=0&size=50
     */
    @GetMapping
    public ResponseEntity<PageResponse<FileResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {

        return ResponseEntity.ok(
                fileService.findAll(page, size)
        );
    }

    /**
     * Get a single file by fileId.
     *
     * Example:
     * GET /api/files/FILE-001
     */
    @GetMapping("/{fileId}")
    public ResponseEntity<FileResponse> findById(
            @PathVariable String fileId) {

        FileResponse result = fileService.findById(fileId);

        if (result == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }
}