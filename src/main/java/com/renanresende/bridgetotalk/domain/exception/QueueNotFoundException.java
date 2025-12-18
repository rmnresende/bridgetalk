package com.renanresende.bridgetotalk.domain.exception;

import java.util.UUID;

public class QueueNotFoundException extends ResourceNotFoundException{

    public QueueNotFoundException(UUID id) {
        super("Queue not found with id: " + id + " in company");
    }
}
