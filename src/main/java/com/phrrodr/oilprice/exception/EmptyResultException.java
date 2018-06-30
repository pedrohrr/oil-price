package com.phrrodr.oilprice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class EmptyResultException extends Exception {

    public EmptyResultException(Throwable cause) {
        super(cause);
    }
}