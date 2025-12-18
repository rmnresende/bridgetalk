package com.renanresende.bridgetotalk.adapter.in.web.dto.company;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ResponseCompanyDto(
        UUID id,
        String name,
        String slug,
        String email,
        String phone,
        String document,
        CompanySettingsUpdateDto settings,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        Instant createdAt,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        Instant updatedAt,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        Instant deletedAt
) {

}
