package com.renanresende.bridgetotalk.adapter.in.web;

import com.renanresende.bridgetotalk.adapter.in.web.dto.AgentDto;
import com.renanresende.bridgetotalk.adapter.in.web.dto.UpdateAgentDto;
import com.renanresende.bridgetotalk.adapter.in.web.mapper.AgentDtoMapper;
import com.renanresende.bridgetotalk.adapter.in.web.mapper.CompanyDtoMapper;
import com.renanresende.bridgetotalk.application.service.ManagmentAgentService;
import com.renanresende.bridgetotalk.domain.AgentStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/agents")
public class AgentController {

    private final ManagmentAgentService service;
    private final AgentDtoMapper mapper;
    private final CompanyDtoMapper companyDtoMapper;

    public AgentController(ManagmentAgentService agentService, AgentDtoMapper mapper, CompanyDtoMapper companyDtoMapper) {
        this.service = agentService;
        this.mapper = mapper;
        this.companyDtoMapper = companyDtoMapper;
    }

    @PostMapping
    public ResponseEntity<AgentDto> create(
            @RequestBody AgentDto request
    ) {

        var command = mapper.toCommand(request);
        var response = service.create(command);

        var uri = URI.create(String.format("/api/v1/agents/%s/company/%s", response.getId(), response.getCompanyId()));

        return ResponseEntity
                .created(uri)
                .body(mapper.toDto(response));
    }

    @GetMapping("/{id}/company/{companyId}")
    public ResponseEntity<AgentDto> getActiveAgentByIdAndCompany(@PathVariable UUID id, @PathVariable UUID companyId){

        var domain = service.getActiveAgent(id, companyId);
        return ResponseEntity.ok(mapper.toDto(domain));
    }

    @GetMapping("/company/{companyId}/email/{email}")
    public ResponseEntity<AgentDto> getActiveAgentByIdAndCompany(@PathVariable UUID companyId, @PathVariable String email){

        var domain = service.findActiveAgentByCompanyIdAndEmail(companyId, email);
        return ResponseEntity.ok(mapper.toDto(domain));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable UUID id,
            @RequestBody UpdateAgentDto request)
    {
        service.updateAgentStatus(id, request.companyId(), request.status());
        return ResponseEntity.noContent().build();
    }
}
