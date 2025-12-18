package com.renanresende.bridgetotalk.application.port.in.command;

import java.util.UUID;

public record UpdateCompanyCommand(
        UUID id,
        String name,
        String email,
        String phone,
        String document,
        String slug
) {}