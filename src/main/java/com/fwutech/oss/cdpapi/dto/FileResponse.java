package com.fwutech.oss.cdpapi.dto;

import java.time.LocalDateTime;

public record FileResponse(
        String fileId,
        String fileName,
        String fileSource,
        LocalDateTime fileDate,
        LocalDateTime fileLoadDate,
        Long recordCount,
        String ingestionStatus
) {
}