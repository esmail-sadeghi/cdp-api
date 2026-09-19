package com.fwutech.oss.cdpapi.repository;

import com.fwutech.oss.cdpapi.model.TrunkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrunkRepository extends JpaRepository<TrunkEntity, Long> {

    Optional<TrunkEntity> findByTrunkId(Long trunkId);
    List<TrunkEntity> findByActiveTrue();
    List<TrunkEntity> findByCustomerId(Long customerId);
    List<TrunkEntity> findBySwitchId(Long switchId);
    List<TrunkEntity> findByTrunkNameContainingIgnoreCase(String trunkName);
    boolean existsByTrunkId(Long trunkId);
}