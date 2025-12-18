package com.renanresende.bridgetotalk.adapter.in.web.dto;

import java.util.Optional;

public record QueryOptions(
        Optional<String> sortBy,
        Optional<String> sortDirection,
        boolean includeInactive
) {}

