package com.zakrzewski.services;

public class NotEnoughFundsException extends RuntimeException {
    public NotEnoughFundsException(String not_enough_funds) {
        super(not_enough_funds);
    }
}
