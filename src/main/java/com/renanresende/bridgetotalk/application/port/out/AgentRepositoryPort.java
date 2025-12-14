package com.renanresende.bridgetotalk.application.port.out;

import com.renanresende.bridgetotalk.domain.Agent;
import com.renanresende.bridgetotalk.domain.AgentStatus;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface AgentRepositoryPort {

    Agent save(Agent agent);

    Optional<Agent> findActiveAgentByIdAndCompanyId(UUID id, UUID companyId);

    Optional<Agent> findActiveAgentByCompanyIdAndEmail(UUID companyId, String email);

    void associateAgentToQueue(UUID agentId, UUID queueId);

    void updateStatus(Agent agent);
}