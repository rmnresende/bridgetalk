package com.renanresende.bridgetotalk.domain;

public enum CompanyStatus {
    ACTIVE,     // empresa ativa e operando normalmente
    SUSPENDED,  // pagamentos atrasados / excedeu limites
    INACTIVE   // soft delete
}
