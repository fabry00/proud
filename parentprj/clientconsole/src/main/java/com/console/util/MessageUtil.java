package com.console.util;

import com.mycompany.commons.DateUtil;

/**
 * Utilities application message
 * Created by Fabrizio Faustinoni on 15/09/2016.
 */
public class MessageUtil {

    public String getMsg(String msg) {
        DateUtil util = new DateUtil();
        String builder = "[ " + util.getNow() + " ] " + msg;
        return builder;
    }
}
