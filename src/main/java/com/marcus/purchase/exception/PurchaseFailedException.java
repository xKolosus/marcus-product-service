package com.marcus.purchase.exception;

public class PurchaseFailedException extends RuntimeException{

    public PurchaseFailedException(String message) {
        super(message);
    }
}
