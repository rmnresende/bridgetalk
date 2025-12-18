package com.renanresende.bridgetotalk.adapter.out.jpa.spec;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;

import java.util.Set;

public class SortParams {

    private static final Set<String> ALLOWED_FIELDS_TO_SORT_AGENTS = Set.of(
            "name",
            "email",
            "createdAt"
    );

    private static final Set<String> ALLOWED_FIELDS_TO_SORT_QUEUES = Set.of(
            "name",
            "createdAt"
    );


    public static String validateFieldToSort(String sortBy, String entityName) {
        if (StringUtils.isBlank(sortBy) || notContainsAllowedField(sortBy, entityName))
            return "name";

        return sortBy;
    }

    public static Sort.Direction validateDirection(String direction) {
        if (direction == null || direction.isBlank()) return Sort.Direction.ASC;

        return switch (direction.toLowerCase()) {
            case "asc" -> Sort.Direction.ASC;
            case "desc" -> Sort.Direction.DESC;
            default -> Sort.Direction.ASC;
        };
    }

    private static boolean notContainsAllowedField(String sortBy, String entityName) {
        return !getAllowedFieldsToSort(entityName).contains(sortBy);
    }

    private static Set<String> getAllowedFieldsToSort(String entityName) {

        switch (entityName) {
            case "queue": return ALLOWED_FIELDS_TO_SORT_QUEUES;
            case "agent": return ALLOWED_FIELDS_TO_SORT_AGENTS;
            default: return Set.of();
        }
    }
}
