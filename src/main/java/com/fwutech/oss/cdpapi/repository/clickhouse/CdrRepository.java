package com.fwutech.oss.cdpapi.repository.clickhouse;

import com.fwutech.oss.cdpapi.dto.CdrResponse;
import com.fwutech.oss.cdpapi.dto.PageResponse;

public interface CdrRepository {

    PageResponse<CdrResponse> findByFileId(
            String fileId,
            int page,
            int size
    );

    PageResponse<CdrResponse> findByAssignmentId(
            String assignmentId,
            int page,
            int size
    );

    PageResponse<CdrResponse> findByCustomerId(
            String customerId,
            int page,
            int size
    );
}