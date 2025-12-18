package com.renanresende.bridgetotalk.application.service;

import com.renanresende.bridgetotalk.adapter.in.web.dto.queue.QueueFilter;
import com.renanresende.bridgetotalk.adapter.out.jpa.QueueRepositoryAdapter;
import com.renanresende.bridgetotalk.application.mapper.QueueCommandMapper;
import com.renanresende.bridgetotalk.application.port.in.ManageQueueUseCase;
import com.renanresende.bridgetotalk.application.port.in.command.CreateQueueCommand;
import com.renanresende.bridgetotalk.application.port.in.command.UpdateQueueCommand;
import com.renanresende.bridgetotalk.application.port.out.CompanyRepositoryPort;
import com.renanresende.bridgetotalk.application.port.out.QueueRepositoryPort;
import com.renanresende.bridgetotalk.domain.Queue;
import com.renanresende.bridgetotalk.domain.exception.CompanyNotFoundException;
import com.renanresende.bridgetotalk.domain.exception.QueueNotFoundException;
import com.renanresende.bridgetotalk.domain.exception.ResourceAlreadyExistsException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class ManagementQueueService implements ManageQueueUseCase {

    private final QueueRepositoryPort queueRepositoryPort;
    private final CompanyRepositoryPort companyRepositoryPort;
    private final QueueCommandMapper mapper;

    public ManagementQueueService(QueueRepositoryAdapter queueRepositoryAdapter,
                                  CompanyRepositoryPort companyRepositoryPort,
                                  QueueCommandMapper mapper) {
        this.queueRepositoryPort = queueRepositoryAdapter;
        this.companyRepositoryPort = companyRepositoryPort;
        this.mapper = mapper;
    }

    @Override
    public Queue createQueue(CreateQueueCommand createQueueCommand) {

        validateQueuePreConditions(createQueueCommand.name(), createQueueCommand.companyId());

        var domain = mapper.fromCreateCommandtoDomain(createQueueCommand);
        return queueRepositoryPort.save(domain);
    }

    @Override
    public Queue updateQueue(UUID queueId, UpdateQueueCommand updateQueueCommand) {

        validateQueueUpdatePreConditions(queueId, updateQueueCommand.companyId(), updateQueueCommand.name());
        var domain = mapper.fromUpdateCommandtoDomain(queueId, updateQueueCommand);
        domain.update(updateQueueCommand.name(), updateQueueCommand.distributionStrategy());

        return queueRepositoryPort.save(domain);
    }

    @Override
    public List<Queue> filterQueuesByCompanyId(QueueFilter queueFilter, UUID companyId) {
        return queueRepositoryPort.filterQueuesByCompanyId(queueFilter, companyId);
    }

    @Override
    public List<Queue> getAllActiveQueuesFromCompany(UUID companyId) {
        return queueRepositoryPort.findAllActiveQueuesByCompanyId(companyId);
    }

    @Override
    public void deleteQueue(UUID queueId, UUID companyId) {
        queueRepositoryPort.findByIdAndCompanyId(queueId, companyId)
                .orElseThrow(() -> new QueueNotFoundException(queueId));

        queueRepositoryPort.deleteQueue(queueId, Instant.now());
    }

    private void validateQueuePreConditions(String name, UUID companyId) {
        companyRepositoryPort.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException(companyId));

        if (existisQueueInCompanyWithSameName(name, companyId)) {
            throw new ResourceAlreadyExistsException("Already exists a queue with same name in this company");
        }
    }

    private void validateQueueUpdatePreConditions(UUID queueId, UUID companyId, String name) {

        queueRepositoryPort.findByIdAndCompanyId(queueId, companyId)
                .orElseThrow(() -> new QueueNotFoundException(queueId));

        if (existisQueueInCompanyWithSameName(name, companyId)) {
            throw new ResourceAlreadyExistsException("Already exists a queue with same name in this company");
        }
    }

    private boolean existisQueueInCompanyWithSameName(String name, UUID companyId) {
        return queueRepositoryPort.findByCompanyIdAndName(companyId, name).isPresent();
    }
}
