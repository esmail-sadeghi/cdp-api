package com.fwutech.oss.cdpapi.repository.clickhouse;

import com.fwutech.oss.cdpapi.dto.FileResponse;
import com.fwutech.oss.cdpapi.dto.PageResponse;

public interface FileRepository {

    PageResponse<FileResponse> findAll(
            int page,
            int size
    );

    FileResponse findById(
            String fileId
    );
}