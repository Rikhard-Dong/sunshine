package com.hfmes.sunshine.exception;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/19 19:07
 */
public class DevcNotFoundException extends CustomException {

    public DevcNotFoundException(String message) {
        super(message);
    }

    public DevcNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
