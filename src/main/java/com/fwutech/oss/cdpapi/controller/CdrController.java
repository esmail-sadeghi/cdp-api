package com.fwutech.oss.cdpapi.controller;

import com.fwutech.oss.cdpapi.dto.CdrResponse;
import com.fwutech.oss.cdpapi.dto.PageResponse;
import com.fwutech.oss.cdpapi.service.CdrService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cdr")
public class CdrController {

    private final CdrService cdrService;

    public CdrController(CdrService cdrService) {
        this.cdrService = cdrService;
    }

    @GetMapping("/file/{fileId}")
    public ResponseEntity<PageResponse<CdrResponse>> findByFileId(
            @PathVariable String fileId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {

        return ResponseEntity.ok(
                cdrService.findByFileId(
                        fileId,
                        page,
                        size
                )
        );
    }

    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<PageResponse<CdrResponse>> findByAssignmentId(
            @PathVariable String assignmentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {

        return ResponseEntity.ok(
                cdrService.findByAssignmentId(
                        assignmentId,
                        page,
                        size
                )
        );
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<PageResponse<CdrResponse>> findByCustomerId(
            @PathVariable String customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {

        return ResponseEntity.ok(
                cdrService.findByCustomerId(
                        customerId,
                        page,
                        size
                )
        );
    }
}