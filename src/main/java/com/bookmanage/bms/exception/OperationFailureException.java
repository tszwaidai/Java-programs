package com.bookmanage.bms.exception;

//运行异常处理
public class OperationFailureException extends RuntimeException{
    public OperationFailureException() {
    }

    public OperationFailureException(String message) {
        super(message);
    }
}
