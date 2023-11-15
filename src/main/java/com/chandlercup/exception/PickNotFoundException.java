package com.chandlercup.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Pick Not Found")
public class PickNotFoundException extends DataIntegrityViolationException {
    public PickNotFoundException(String msg) { super(msg); }

    public PickNotFoundException(String msg, Throwable cause) { super(msg, cause); }
}
