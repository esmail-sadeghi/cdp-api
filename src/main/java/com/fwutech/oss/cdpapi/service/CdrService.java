package com.fwutech.oss.cdpapi.service;

import com.fwutech.oss.cdpapi.dto.CdrResponse;
import com.fwutech.oss.cdpapi.dto.PageResponse;
import com.fwutech.oss.cdpapi.repository.clickhouse.CdrRepository;
import org.springframework.stereotype.Service;

@Service
public class CdrService {

    private final CdrRepository cdrRepository;

    public CdrService(CdrRepository cdrRepository) {
        this.cdrRepository = cdrRepository;
    }

    /**
     * Find CDR records belonging to a specific file.
     *
     * Example:
     * GET /api/cdr/file/12345?page=0&size=100
     */
    public PageResponse<CdrResponse> findByFileId(
            String fileId,
            int page,
            int size) {

        validateId(fileId, "fileId");
        validatePagination(page, size);

        return cdrRepository.findByFileId(
                fileId,
                page,
                size
        );
    }

    /**
     * Find CDR records belonging to a specific assignment.
     *
     * Example:
     * GET /api/cdr/assignment/ASSIGN-001?page=0&size=100
     */
    public PageResponse<CdrResponse> findByAssignmentId(
            String assignmentId,
            int page,
            int size) {

        validateId(assignmentId, "assignmentId");
        validatePagination(page, size);

        return cdrRepository.findByAssignmentId(
                assignmentId,
                page,
                size
        );
    }

    /**
     * Find CDR records belonging to a specific customer.
     *
     * Example:
     * GET /api/cdr/customer/CUSTOMER-001?page=0&size=100
     */
    public PageResponse<CdrResponse> findByCustomerId(
            String customerId,
            int page,
            int size) {

        validateId(customerId, "customerId");
        validatePagination(page, size);

        return cdrRepository.findByCustomerId(
                customerId,
                page,
                size
        );
    }

    /**
     * Validate identifier values.
     */
    private void validateId(
            String value,
            String fieldName) {

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    fieldName + " must not be blank"
            );
        }
    }

    /**
     * Validate pagination parameters.
     *
     * page:
     *   zero-based page number
     *
     * size:
     *   number of records per page
     */
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

        /*
         * Prevent very large queries against ClickHouse.
         */
        if (size > 1000) {
            throw new IllegalArgumentException(
                    "size must not be greater than 1000"
            );
        }
    }
}