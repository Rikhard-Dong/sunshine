package com.hfmes.sunshine.exception;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/20 8:57
 */
public class TaskNotFoundException extends CustomException {
    public TaskNotFoundException(String message) {
        super(message);
    }

    public TaskNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
