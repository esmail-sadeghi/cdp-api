package com.fwutech.oss.cdpapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CsTrunkItem(

        @JsonProperty("trunk_id")
        Long trunkId,

        @JsonProperty("trunk_name")
        String trunkName,

        @JsonProperty("switch_id")
        Long switchId,

        @JsonProperty("group_type")
        String groupType,

        @JsonProperty("assignment_id")
        Long assignmentId,

        @JsonProperty("customer_id")
        Long customerId,

        @JsonProperty("from_date")
        OffsetDateTime fromDate,

        @JsonProperty("to_date")
        OffsetDateTime toDate
) {
}