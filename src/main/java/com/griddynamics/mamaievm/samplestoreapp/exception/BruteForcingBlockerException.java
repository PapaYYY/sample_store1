package com.griddynamics.mamaievm.samplestoreapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BruteForcingBlockerException extends RuntimeException {

    public BruteForcingBlockerException(String message) {
        super(message);
    }

}
