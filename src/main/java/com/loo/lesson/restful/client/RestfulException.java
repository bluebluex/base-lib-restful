package com.loo.lesson.restful.client;

/**
 * @program: base-core
 * @description:
 * @author: Tomax
 * @create: 2018-08-02 17:29
 **/
public class RestfulException extends Exception {

    private static final long serialVersionUID = 8578184677834540241L;

    public RestfulException() {
    }

    public RestfulException(String message) {
        super(message);
    }

    public RestfulException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestfulException(Throwable cause) {
        super(cause);
    }
}
