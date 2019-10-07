package com.cisco.auth.exceptions;

public class ServiceException extends RuntimeException {

    private String domain;

    public ServiceException(Class clazz, String s) {
        super(s);
        domain = clazz.getName();
    }

    public String getDomain() {
        return domain;
    }
}