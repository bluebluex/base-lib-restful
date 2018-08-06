package com.loo.lesson.restful.exception;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-03 10:39
 **/
public class ServiceException extends Exception {
    private static final long serialVersionUID = 5570438751275436432L;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
