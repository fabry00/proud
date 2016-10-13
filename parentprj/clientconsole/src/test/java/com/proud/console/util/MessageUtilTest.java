package com.proud.console.util;

import com.proud.commons.DateUtils;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for message util test
 * Created by Fabrizio Faustinoni on 13/10/2016.
 */
public class MessageUtilTest {

    @Test
    public void getMsg() throws Exception {
        String msg = "Message";
        DateUtils util = new DateUtils();
        String expected = "[ " + util.getNow() + " ] " + msg;
        assertEquals(expected,new MessageUtil().getMsg(msg));
    }

}