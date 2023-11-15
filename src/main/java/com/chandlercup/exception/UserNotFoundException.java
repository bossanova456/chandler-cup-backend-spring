package com.chandlercup.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User Not Found")
public class UserNotFoundException extends DataIntegrityViolationException {
    public UserNotFoundException(String msg) { super(msg); }

    public UserNotFoundException(String msg, Throwable cause) { super(msg, cause); }
}
