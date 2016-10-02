package com.proud.console.domain;

/**
 *
 * @author Fabrizio Faustinoni
 */
public interface ICallback {

    void success(Object obj);

    void fail(String message);
}
