package com.fwutech.oss.cdpapi.controller;

import com.fwutech.oss.cdpapi.model.TrunkEntity;
import com.fwutech.oss.cdpapi.service.TrunkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trunks")
public class TrunkController {

    private final TrunkService trunkService;

    public TrunkController(TrunkService trunkService) {
        this.trunkService = trunkService;
    }

    @GetMapping
    public ResponseEntity<List<TrunkEntity>> findAll() {
        return ResponseEntity.ok(
                trunkService.findAll()
        );
    }

    @GetMapping("/active")
    public ResponseEntity<List<TrunkEntity>> findActive() {
        return ResponseEntity.ok(
                trunkService.findActive()
        );
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<TrunkEntity>> findByCustomer(
            @PathVariable Long customerId
    ) {
        return ResponseEntity.ok(
                trunkService.findByCustomerId(customerId)
        );
    }

    @GetMapping("/switch/{switchId}")
    public ResponseEntity<List<TrunkEntity>> findBySwitch(
            @PathVariable Long switchId
    ) {
        return ResponseEntity.ok(
                trunkService.findBySwitchId(switchId)
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<TrunkEntity>> search(
            @RequestParam String name
    ) {
        return ResponseEntity.ok(
                trunkService.searchByName(name)
        );
    }

    @PostMapping("/sync")
    public ResponseEntity<TrunkService.SyncResult> sync() {

        return ResponseEntity.ok(
                trunkService.syncTrunks()
        );
    }
}