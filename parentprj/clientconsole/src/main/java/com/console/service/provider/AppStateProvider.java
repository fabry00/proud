package com.console.service.provider;

import com.console.domain.ICallback;
import com.console.domain.AppNode;
import com.console.domain.AppState;
import javafx.application.Platform;

/**
 *
 * @author fabry
 */
public class AppStateProvider {

    /**
     * TODO retreive from server
     *
     * @param callback
     */
    public void getSystemState(final ICallback callback) {

        Platform.runLater(() -> {
            AppState state = new AppState.Builder().build();

            state.addNodeData(new AppNode.Builder("Homer")
                    .withInfo(new AppNode.NodeInfo(AppNode.NodeInfo.Type.IP, "192.168.1.101"))
                    .build());

            state.addNodeData(new AppNode.Builder("Lisa")
                    .withInfo(new AppNode.NodeInfo(AppNode.NodeInfo.Type.IP, "192.168.1.102"))
                    .build());

            state.addNodeData(new AppNode.Builder("Marge")
                    .withInfo(new AppNode.NodeInfo(AppNode.NodeInfo.Type.IP, "192.168.1.103"))
                    .build());

            state.addNodeData(new AppNode.Builder("Bart")
                    .withInfo(new AppNode.NodeInfo(AppNode.NodeInfo.Type.IP, "192.168.1.104"))
                    .build());

            state.addNodeData(new AppNode.Builder("Meggie")
                    .withInfo(new AppNode.NodeInfo(AppNode.NodeInfo.Type.IP, "192.168.1.105"))
                    .build());

            callback.success(state);
        });

    }
}
