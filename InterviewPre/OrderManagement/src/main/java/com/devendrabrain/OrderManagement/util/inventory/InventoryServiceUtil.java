package com.devendrabrain.OrderManagement.util.inventory;

import com.devendrabrain.OrderManagement.util.customermgr.CustomerDTO;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

public class InventoryServiceUtil {

    @Autowired
    Environment environment;

    public ResponseEntity<InventoryDTO> fetchInventoryRecords(Long inventoryId) {

        HttpHeaders headers = new HttpHeaders();
        /*
         * Setting Request in headers for backtracking
         */
        headers.set("X-RS-RequestId", MDC.get("X-RS-TraceId"));
        /*
         * Set Source Application in headers, so that we can save it
         * EventHttpLog, which application we are calling
         */
        headers.set("X-RS-SourceApplication", "InventoryMgr");
        headers.set("X-RS-ApiName", "Outbound_IVT_MGR_FETCH_BY_ID");

        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);

        ResponseEntity<InventoryDTO> responseEntity = null;
        RestTemplate restTemplate = new RestTemplate();

        try {
            responseEntity = restTemplate.exchange(new URI(
                            "http://localhost:8080/InventoryMgr/" + inventoryId),
                    HttpMethod.GET, requestEntity, InventoryDTO.class);
        } catch (RestClientException | IllegalStateException | URISyntaxException e) {
           /* log.error("ClassName : " + this.getClass().getSimpleName() + " Exception in calling OrgMgr", e);
            throw new ApiInternalErrorException(ErrorCategory.InternalServerError.getCode(),
                    ErrorCategory.InternalServerError.getMessage(), e.getMessage() + " Exception in calling OrgMgr");*/
        } catch (Exception e) {
           /* log.error("ClassName : " + this.getClass().getSimpleName()
                    + " OrgMgr is down/Unknown exception while calling OrgMgr", e);
            throw new ApiInternalErrorException(ErrorCategory.InternalServerError.getCode(),
                    ErrorCategory.InternalServerError.getMessage(), "OrgMgr is down/Unknown exception");*/
        }

        return responseEntity;
    }
}
