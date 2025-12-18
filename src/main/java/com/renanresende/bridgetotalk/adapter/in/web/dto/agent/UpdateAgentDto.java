package com.renanresende.bridgetotalk.adapter.in.web.dto.agent;

import com.renanresende.bridgetotalk.domain.AgentStatus;

import java.util.UUID;

public record UpdateAgentDto (
        UUID companyId,

        AgentStatus status
) {}
