package com.chandlercup.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Team Not Found")
public class TeamNotFoundException extends DataIntegrityViolationException {
    public TeamNotFoundException(String msg) { super(msg); }

    public TeamNotFoundException(String msg, Throwable cause) { super(msg, cause); }
}
