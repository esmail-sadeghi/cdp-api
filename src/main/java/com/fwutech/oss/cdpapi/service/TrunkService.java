
package com.fwutech.oss.cdpapi.service;

import com.fwutech.oss.cdpapi.model.TrunkEntity;
import com.fwutech.oss.cdpapi.repository.TrunkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TrunkService {

    private final TrunkRepository trunkRepository;

    public List<TrunkEntity> findAll() {
        return trunkRepository.findAll();
    }

    public List<TrunkEntity> findActive() {
        return trunkRepository.findByActiveTrue();
    }

    public List<TrunkEntity> findByCustomerId(Long customerId) {
        return trunkRepository.findByCustomerId(customerId);
    }

    public List<TrunkEntity> findBySwitchId(Long switchId) {
        return trunkRepository.findBySwitchId(switchId);
    }

    public List<TrunkEntity> searchByName(String name) {
        if (name == null || name.isBlank()) {
            return List.of();
        }

        return trunkRepository.findByTrunkNameContainingIgnoreCase(
                name.trim()
        );
    }
}
