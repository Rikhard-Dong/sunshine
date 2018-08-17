package com.hfmes.sunshine.exception;


/**
 * @author supreDong@gmail.com
 * @date 2018/8/17 8:43
 */
public class CustomException extends RuntimeException {

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
