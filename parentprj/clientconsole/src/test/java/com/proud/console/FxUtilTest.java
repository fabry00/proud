package com.proud.console;

import javafx.embed.swing.JFXPanel;
import org.junit.BeforeClass;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Base class for javafx tests
 * Created by Fabrizio Faustinoni on 13/10/2016.
 */
public abstract class FxUtilTest {
    protected static JFXPanel panel;
    @BeforeClass
    public static void setUpClass()
            throws InterruptedException
    {
        final CountDownLatch latch = new CountDownLatch(1);
        SwingUtilities.invokeLater(() -> {
            panel = new JFXPanel(); // initializes JavaFX environment
            latch.countDown();
        });

        // That's a pretty reasonable delay... Right?
        if (!latch.await(5L, TimeUnit.SECONDS))
            throw new ExceptionInInitializerError();
    }
}
