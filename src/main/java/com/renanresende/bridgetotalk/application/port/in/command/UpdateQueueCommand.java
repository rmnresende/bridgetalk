package com.renanresende.bridgetotalk.application.port.in.command;

import com.renanresende.bridgetotalk.domain.DistributionStrategy;

import java.util.UUID;

public record UpdateQueueCommand (
        UUID id,
        UUID companyId,
        String name,
        DistributionStrategy distributionStrategy
) {
}
