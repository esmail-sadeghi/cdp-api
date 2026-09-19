package com.fwutech.oss.cdpapi.scheduler;

import com.fwutech.oss.cdpapi.service.TrunkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TrunkSyncScheduler {

    private static final Logger log = LoggerFactory.getLogger(TrunkSyncScheduler.class);
    private final TrunkService trunkService;

    public TrunkSyncScheduler(TrunkService trunkService) {
        this.trunkService = trunkService;
    }

    @Scheduled(
            cron = "${trunk.sync.cron:0 0 7 * * *}",
            zone = "${trunk.sync.zone:Asia/Tehran}"
    )
    public void sync() {
        log.info("Scheduled trunk synchronization started");

        try {
            TrunkService.SyncResult result =
                    trunkService.syncTrunks();
            log.info(
                    "Scheduled trunk synchronization completed: {}",
                    result
            );

        } catch (Exception e) {
            log.error(
                    "Scheduled trunk synchronization failed", e
            );
        }
    }
}
