package com.fwutech.oss.cdpapi.service;

import com.fwutech.oss.cdpapi.client.OssInventoryClient;
import com.fwutech.oss.cdpapi.dto.CsTrunkItem;
import com.fwutech.oss.cdpapi.dto.CsTrunksResponse;
import com.fwutech.oss.cdpapi.model.TrunkEntity;
import com.fwutech.oss.cdpapi.repository.TrunkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TrunkService {

    private static final Logger log =
            LoggerFactory.getLogger(TrunkService.class);

    private final TrunkRepository trunkRepository;
    private final OssInventoryClient ossInventoryClient;

    public TrunkService(
            TrunkRepository trunkRepository,
            OssInventoryClient ossInventoryClient
    ) {
        this.trunkRepository = trunkRepository;
        this.ossInventoryClient = ossInventoryClient;
    }

    @Transactional
    public SyncResult syncTrunks() {
        log.info("Starting trunk synchronization");
        CsTrunksResponse response = ossInventoryClient.getTrunks();
        List<CsTrunkItem> items = response.items();
        int inserted = 0;
        int updated = 0;

        for (CsTrunkItem item : items) {
            if (item.trunkId() == null) {
                log.warn("Skipping trunk without trunk_id");
                continue;
            }

            TrunkEntity entity =
                    trunkRepository.findByTrunkId(item.trunkId())
                            .orElse(null);

            if (entity == null) {
                entity = new TrunkEntity();
                entity.setTrunkId(item.trunkId());
                map(item, entity);
                trunkRepository.save(entity);
                inserted++;
                log.debug(
                        "Inserted trunk: id={}, name={}",
                        item.trunkId(),
                        item.trunkName()
                );
            } else {
                map(item, entity);
                trunkRepository.save(entity);
                updated++;
                log.debug(
                        "Updated trunk: id={}, name={}",
                        item.trunkId(),
                        item.trunkName()
                );
            }
        }

        log.info(
                "Trunk synchronization completed. received={}, inserted={}, updated={}",
                items.size(),
                inserted,
                updated
        );

        return new SyncResult(
                items.size(),
                inserted,
                updated
        );
    }

    private void map(
            CsTrunkItem source,
            TrunkEntity target
    ) {
        target.setTrunkName(source.trunkName());
        target.setSwitchId(source.switchId());
        target.setGroupType(source.groupType());
        target.setAssignmentId(source.assignmentId());
        target.setCustomerId(source.customerId());
        target.setFromDate(source.fromDate());
        target.setToDate(source.toDate());

        target.setActive(
                source.toDate() == null
        );
    }

    @Transactional(readOnly = true)
    public List<TrunkEntity> findAll() {
        return trunkRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<TrunkEntity> findActive() {
        return trunkRepository.findByActiveTrue();
    }

    @Transactional(readOnly = true)
    public List<TrunkEntity> findByCustomerId(Long customerId) {
        return trunkRepository.findByCustomerId(customerId);
    }

    @Transactional(readOnly = true)
    public List<TrunkEntity> findBySwitchId(Long switchId) {
        return trunkRepository.findBySwitchId(switchId);
    }

    @Transactional(readOnly = true)
    public List<TrunkEntity> searchByName(String name) {
        return trunkRepository.findByTrunkNameContainingIgnoreCase(name);
    }

    public record SyncResult(
            int received,
            int inserted,
            int updated
    ) {
    }
}