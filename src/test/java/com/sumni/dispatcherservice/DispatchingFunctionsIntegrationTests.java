package com.sumni.dispatcherservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.UUID;
import java.util.function.Function;

@FunctionalSpringBootTest
public class DispatchingFunctionsIntegrationTests {
    @Autowired
    private FunctionCatalog catalog;
    
    @Test
    void packAndLabelOrder() {
        Function<OrderAcceptedMessage, Flux<OrderDispatchedMessage>> packAndLabel = catalog.lookup(
                Function.class,
                "pack|label"
        );
        
        UUID orderId = UUID.randomUUID();
        
        StepVerifier
                .create(
                        packAndLabel.apply(new OrderAcceptedMessage(orderId))
                )
                .expectNextMatches(orderDispatchedMessage ->
                        orderDispatchedMessage.equals(new OrderDispatchedMessage(orderId))
                )
                .verifyComplete();
    }
}
