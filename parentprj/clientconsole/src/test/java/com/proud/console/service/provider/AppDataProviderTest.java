package com.proud.console.service.provider;

import com.proud.console.FxUtilTest;
import com.proud.console.domain.*;
import com.proud.domain.PredictionType;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;

/**
 * Test AppDataProvider
 * Created by Fabrizio Faustinon on 13/10/2016.
 */
public class AppDataProviderTest extends FxUtilTest {

    @Test
    public void getSystemState() throws InterruptedException {
        final AtomicBoolean finished = new AtomicBoolean(false);
        final AtomicReference<AppState> state = new AtomicReference<>();
        final AtomicReference<String> failMessage = new AtomicReference<>();
        new AppDataProvider().getSystemState(new ICallback() {
            @Override
            public void success(Object obj) {
                state.set((AppState) obj);
                finished();

            }

            @Override
            public void fail(String message) {
                failMessage.set(message);
                finished();
            }

            private void finished() {
                synchronized (finished) {
                    finished.set(true);
                    finished.notify();
                }
            }
        });
        synchronized (finished) {
            while (!finished.get()) {
                finished.wait();
            }
        }
        if(failMessage.get() != null) {
            fail(failMessage.get());
            return;
        }

        assertEquals(State.UNKNOWN, state.get().getState());
        assertEquals("FailurePrediction", PredictionType.DETECTED, state.get().getFailurePrediction());
        assertEquals("System failure",PredictionType.NOT_DETECTED, state.get().getSystemFailure());

    }
}