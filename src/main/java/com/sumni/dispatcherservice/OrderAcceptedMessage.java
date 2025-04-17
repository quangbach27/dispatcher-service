package com.sumni.dispatcherservice;

import java.util.UUID;

public record OrderAcceptedMessage(
        UUID orderId
) {
}
