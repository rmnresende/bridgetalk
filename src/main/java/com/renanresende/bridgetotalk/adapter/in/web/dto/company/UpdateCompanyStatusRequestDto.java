package com.renanresende.bridgetotalk.adapter.in.web.dto.company;

import com.renanresende.bridgetotalk.adapter.in.web.validation.ValidEnum;
import com.renanresende.bridgetotalk.domain.CompanyStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateCompanyStatusRequestDto(
        @NotNull(message = "Status is required")
        CompanyStatus status
){}

