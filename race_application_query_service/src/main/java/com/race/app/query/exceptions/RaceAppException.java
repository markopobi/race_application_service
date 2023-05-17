package com.race.app.query.exceptions;

public class RaceAppException extends RuntimeException
{
    public RaceAppException(){
        super();
    }

    public RaceAppException(String message){
        super(message);
    }

    public RaceAppException(String message, Throwable cause){
        super(message, cause);
    }
}
