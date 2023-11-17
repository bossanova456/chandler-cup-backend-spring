package com.chandlercup.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Pick team not found for given matchup")
public class PickInvalidTeamException extends DataIntegrityViolationException {
    public PickInvalidTeamException(String msg) { super(msg); }

    public PickInvalidTeamException(String msg, Throwable cause) { super(msg, cause); }
}
