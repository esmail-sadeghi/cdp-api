package com.fwutech.oss.cdpapi.dto;

import java.time.LocalDateTime;

public record CdrResponse(
        String fileId,
        Long recordId,
        String assignmentId,
        String customerId,
        String callingNumber,
        String calledNumber,
        LocalDateTime callStartTime,
        LocalDateTime callEndTime,
        Long duration,
        String inTrunk,
        String outTrunk,
        LocalDateTime ingestionTime
) {
}