package com.proud.console.domain;

/**
 *
 * @author Fabrizio Faustinoni
 */
public interface IAppStateListener {

    void AppEvent(AppEvent event, Object param);
}
