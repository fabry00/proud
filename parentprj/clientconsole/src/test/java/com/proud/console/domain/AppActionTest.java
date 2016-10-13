package com.proud.console.domain;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by fabry on 13/10/2016.
 */
public class AppActionTest {

    @Test
    public void toStringTest() throws Exception {
        AppAction action = new AppAction<>(ActionType.CLOSE, null);
        assertEquals("CLOSE",action.toString());
    }

}