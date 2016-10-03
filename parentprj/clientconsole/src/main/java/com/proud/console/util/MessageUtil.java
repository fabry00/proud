package com.proud.console.util;

import com.proud.commons.DateUtils;

/**
 * Utilities application message
 * Created by Fabrizio Faustinoni on 15/09/2016.
 */
public class MessageUtil {

    public String getMsg(String msg) {
        DateUtils util = new DateUtils();
        String builder = "[ " + util.getNow() + " ] " + msg;
        return builder;
    }
}
