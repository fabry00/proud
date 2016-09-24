package com.console.domain;

/**
 *
 * @author fabry
 */
public interface ICallback {
    
    public void success(Object obj);
    
    public void fail(String message);
}
