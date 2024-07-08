package com.amigoscode.fraud;

import com.amigoscode.clients.fraud.FraudCheckResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/fraud-check")
@AllArgsConstructor
@Slf4j
public class FraudController {

    private final FraudCheckHistoryServices fraudCheckHistoryServices;


    @GetMapping (path = "{customerId}")
    public FraudCheckResponse isFraudster(
            @PathVariable("customerId") Integer customerId ){
        Boolean isFraudulentCustomer = fraudCheckHistoryServices
                .isFraudulentCustomer(customerId);
        log.info("fraud check for customer",customerId);
        return new FraudCheckResponse(isFraudulentCustomer);
    }
}
