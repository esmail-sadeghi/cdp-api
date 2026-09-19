package com.fwutech.oss.cdpapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(
        name = "trunk",
        indexes = {
                @Index(name = "idx_trunk_name", columnList = "trunk_name"),
                @Index(name = "idx_trunk_switch_id", columnList = "switch_id"),
                @Index(name = "idx_trunk_customer_id", columnList = "customer_id"),
                @Index(name = "idx_trunk_assignment_id", columnList = "assignment_id"),
                @Index(name = "idx_trunk_dates", columnList = "from_date,to_date")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrunkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trunk_id", nullable = false, unique = true)
    private Long trunkId;

    @Column(name = "trunk_name", length = 255)
    private String trunkName;

    @Column(name = "switch_id")
    private Long switchId;

    @Column(name = "group_type", length = 50)
    private String groupType;

    @Column(name = "assignment_id")
    private Long assignmentId;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "from_date")
    private OffsetDateTime fromDate;

    @Column(name = "to_date")
    private OffsetDateTime toDate;

    @Column(name = "active", nullable = false)
    @Builder.Default
    private Boolean active = true;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        OffsetDateTime now = OffsetDateTime.now();

        if (createdAt == null) {
            createdAt = now;
        }

        if (updatedAt == null) {
            updatedAt = now;
        }

        if (active == null) {
            active = true;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}