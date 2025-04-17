package com.sumni.dispatcherservice;

import java.util.UUID;

public record OrderDispatchedMessage(
        UUID orderId
) {
}
