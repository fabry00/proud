package com.proud.console.domain;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Element info test
 * Created by Fabrizio Faustinoni on 13/10/2016.
 */
public class ElementInfoTest {

    @Test
    public void getValue() throws Exception {
        ElementInfo info = new ElementInfo(ElementInfo.Type.IP,"111");
        assertEquals("111", info.getValue());
    }

    @Test
    public void getType() throws Exception {
        ElementInfo info = new ElementInfo(ElementInfo.Type.IP,"111");
        assertEquals(ElementInfo.Type.IP, info.getType());
    }

}