package com.console.domain;

/**
 *
 * @author fabry
 */
public interface IAppStateListener {

    public void AppEvent(AppEvent event, Object param);
}
