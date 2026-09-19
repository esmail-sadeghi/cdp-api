package com.fwutech.oss.cdpapi.client;

import com.fwutech.oss.cdpapi.dto.CsTrunksResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class OssInventoryClient {

    private static final Logger log = LoggerFactory.getLogger(OssInventoryClient.class);
    private final RestClient restClient;
    private final String baseUrl;

    public OssInventoryClient(
            RestClient restClient,
            @Value("${oss-inventory.base-url}") String baseUrl
    ) {
        this.restClient = restClient;
        this.baseUrl = baseUrl;
    }

    public CsTrunksResponse getTrunks() {
        log.info("Requesting trunks from OSS Inventory: {}", baseUrl + "/oss_inv/cs-trunks");
        CsTrunksResponse response = restClient
                .get()
                .uri(baseUrl + "/oss_inv/cs-trunks")
                .retrieve()
                .body(CsTrunksResponse.class);

        if (response == null) {
            throw new IllegalStateException(
                    "OSS Inventory returned an empty response"
            );
        }

        if (response.items() == null) {
            throw new IllegalStateException(
                    "OSS Inventory response does not contain items"
            );
        }

        log.info("Received {} trunks from OSS Inventory",
                response.items().size());

        return response;
    }
}
