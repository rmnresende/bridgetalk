package com.renanresende.bridgetotalk.domain.exception;

import java.util.UUID;

public class CompanyNotFoundException extends ResourceNotFoundException{
    public CompanyNotFoundException(UUID id) {
        super("Company not found with id: " + id);
    }
}
