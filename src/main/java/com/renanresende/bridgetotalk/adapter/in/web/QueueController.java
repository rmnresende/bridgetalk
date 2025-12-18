package com.renanresende.bridgetotalk.adapter.in.web;

import com.renanresende.bridgetotalk.adapter.in.web.dto.QueryOptions;
import com.renanresende.bridgetotalk.adapter.in.web.dto.queue.CreateQueueDto;
import com.renanresende.bridgetotalk.adapter.in.web.dto.queue.QueueFilter;
import com.renanresende.bridgetotalk.adapter.in.web.dto.queue.ResponseQueueDto;
import com.renanresende.bridgetotalk.adapter.in.web.dto.queue.UpdateQueueDto;
import com.renanresende.bridgetotalk.adapter.in.web.mapper.QueueDtoMapper;
import com.renanresende.bridgetotalk.application.port.in.ManageQueueUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/queues")
public class QueueController {

    private final ManageQueueUseCase service;
    private final QueueDtoMapper mapper;

    public QueueController(ManageQueueUseCase service,
                           QueueDtoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ResponseQueueDto> createQueue(@RequestBody CreateQueueDto request){

        var command = mapper.toCreateCommand(request);
        var domain = service.createQueue(command);

        var uri = URI.create(String.format("/api/v1/queue/%s/company/%s", domain.getId(), domain.getCompanyId()));

        return ResponseEntity
                .created(uri)
                .body(mapper.toResponseDto(domain));
    }

    @GetMapping("/active/company/{companyId}")
    public ResponseEntity<List<ResponseQueueDto>> listQueuesByCompany(@PathVariable UUID companyId){

        var response = service.getAllActiveQueuesFromCompany(companyId)
                .stream()
                .map(mapper::toResponseDto)
                .toList();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{queueId}")
    public ResponseEntity<Void> updateQueue(@PathVariable UUID queueId,
                                            @Valid @RequestBody UpdateQueueDto request){

        var command = mapper.toUpdateCommand(queueId, request);
        var response = service.updateQueue(queueId, command);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{queueId}/company/{companyId}")
    public ResponseEntity<Void> deleteQueue(@PathVariable UUID queueId,
                                            @PathVariable UUID companyId){

        service.deleteQueue(queueId, companyId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<ResponseQueueDto>> filterQueuesByCompany(@PathVariable UUID companyId,
                                                                @RequestParam(required = false) String name,
                                                                @RequestParam(required = false) boolean inactive,
                                                                @RequestParam(required = false) String sortBy,
                                                                @RequestParam(required = false) String sortDirection){
        var filter = new QueueFilter(
                Optional.ofNullable(name),
                new QueryOptions(
                        Optional.ofNullable(sortBy),
                        Optional.ofNullable(sortDirection),
                        inactive
                )
        );

        var response = service.filterQueuesByCompanyId(filter, companyId)
                .stream()
                .map(mapper::toResponseDto)
                .toList();

        return ResponseEntity.ok(response);
    }
}
