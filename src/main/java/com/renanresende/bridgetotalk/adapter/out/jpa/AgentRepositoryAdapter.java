package com.renanresende.bridgetotalk.adapter.out.jpa;

import com.renanresende.bridgetotalk.adapter.out.jpa.mapper.AgentJpaMapper;
import com.renanresende.bridgetotalk.application.port.out.AgentRepositoryPort;
import com.renanresende.bridgetotalk.domain.Agent;
import com.renanresende.bridgetotalk.domain.AgentStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
public class AgentRepositoryAdapter implements AgentRepositoryPort {

    private final SpringDataAgentRepository repository;
    private final AgentJpaMapper mapper;

    public AgentRepositoryAdapter(SpringDataAgentRepository repository, AgentJpaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Agent save(Agent agent) {
        var entity = mapper.toEntity(agent);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Agent> findActiveAgentByIdAndCompanyId(UUID id, UUID companyId) {
        return repository.findActiveAgentByIdAndCompanyId(id, companyId)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Agent> findActiveAgentByCompanyIdAndEmail(UUID companyId, String email) {
        return repository.findActiveByEmailAndCompanyId(email, companyId)
                .map(mapper::toDomain);
    }

    @Override
    public void associateAgentToQueue(UUID agentId, UUID queueId) {

    }

    @Override
    @Transactional
    public void updateStatus(Agent agent) {
        repository.updateStatus(agent.getId(), agent.getStatus(), agent.getUpdatedAt());
    }
}
