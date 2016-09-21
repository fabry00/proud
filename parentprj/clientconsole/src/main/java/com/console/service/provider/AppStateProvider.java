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

            AppNode homer = new AppNode.Builder("Homer")
                    .withInfo(new AppNode.NodeInfo(AppNode.NodeInfo.Type.IP, "192.168.1.101"))
                    .build();

            AppNode lisa = new AppNode.Builder("Lisa")
                    .withInfo(new AppNode.NodeInfo(AppNode.NodeInfo.Type.IP, "192.168.1.101"))
                    .build();

            AppNode marge = new AppNode.Builder("Marge")
                    .withInfo(new AppNode.NodeInfo(AppNode.NodeInfo.Type.IP, "192.168.1.101"))
                    .build();

            AppNode bart = new AppNode.Builder("Bart")
                    .withInfo(new AppNode.NodeInfo(AppNode.NodeInfo.Type.IP, "192.168.1.101"))
                    .build();

            AppNode meggie = new AppNode.Builder("Meggie")
                    .withInfo(new AppNode.NodeInfo(AppNode.NodeInfo.Type.IP, "192.168.1.101"))
                    .build();

            homer.getConnections().add(bart);
            lisa.getConnections().add(meggie);

            state.addNodeData(homer);
            state.addNodeData(lisa);
            state.addNodeData(marge);
            state.addNodeData(bart);
            state.addNodeData(meggie);

            callback.success(state);
        });

    }
}
