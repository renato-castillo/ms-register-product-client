package com.bootcamp.msregisterproductclient.exception;


public class ModelNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Model not found";

    public ModelNotFoundException() {
        super(MESSAGE);
    }
}
