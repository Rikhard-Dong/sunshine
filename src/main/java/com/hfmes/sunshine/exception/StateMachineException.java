package com.hfmes.sunshine.exception;

/**
 * @author supreDong@gmail.com
 * @date 2018/8/17 15:18
 */
public class StateMachineException extends CustomException {

    public StateMachineException(String message) {
        super(message);
    }

    public StateMachineException(String message, Throwable cause) {
        super(message, cause);
    }
}
