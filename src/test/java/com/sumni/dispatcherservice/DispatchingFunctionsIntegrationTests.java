package com.sumni.dispatcherservice;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.UUID;
import java.util.function.Function;

@FunctionalSpringBootTest
@Disabled("These tests are only necessary when using the functions alone (no bindings)")
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
                        orderDispatchedMessage.orderId().equals(orderId)
                )
                .verifyComplete();
    }
}
