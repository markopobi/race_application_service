package com.race.app.query.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RaceApplicationNotFoundException extends RaceAppException {

    public RaceApplicationNotFoundException(){
        super();
    }

    public RaceApplicationNotFoundException(String message){
        super(message);
    }

    public RaceApplicationNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
