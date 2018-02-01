package com.marcoscarneiro.cursomc.services.exceptions;

public class DataIntegrityException extends RuntimeException {

    private final static long serialVersionUID = 1L;

    public DataIntegrityException(String msg){
        super(msg);
    }

    public DataIntegrityException(String msg, Throwable cause){
        super(msg, cause);
    }
}
