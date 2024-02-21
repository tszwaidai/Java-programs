package com.bookmanage.bms.exception;

/**
 * 库存不足异常
 *
 */
//RuntimeException运行时异常
public class NotEnoughException extends RuntimeException{
    public NotEnoughException() {
        super();
    }

    public NotEnoughException(String message) {
        super(message);
    }
}
