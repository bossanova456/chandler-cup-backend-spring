package com.chandlercup.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Matchup Not Found")
public class MatchupNotFoundException extends DataIntegrityViolationException {
    public MatchupNotFoundException(String msg) { super(msg); }

    public MatchupNotFoundException(String msg, Throwable cause) { super(msg, cause); }
}
