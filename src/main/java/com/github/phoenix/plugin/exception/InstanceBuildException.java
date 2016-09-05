package com.github.phoenix.plugin.exception;


public class InstanceBuildException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    public InstanceBuildException(Exception e) {
        super(e);
    }

}
