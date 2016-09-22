package com.console.service.provider;

import com.console.domain.AppElement;
import com.console.domain.AppLayer;
import com.console.domain.ICallback;
import com.console.domain.AppNode;
import com.console.domain.AppState;
import com.console.domain.ElementInfo;
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

            AppElement homer = new AppNode.Builder("Homer")
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.101"))
                    .build();

            AppElement lisa = new AppNode.Builder("Lisa")
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.102"))
                    .build();

            AppElement marge = new AppNode.Builder("Marge")
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.103"))
                    .build();

            AppElement bart = new AppNode.Builder("Bart")
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.104"))
                    .build();

            AppElement meggie = new AppNode.Builder("Meggie")
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.105"))
                    .build();

            AppElement node1 = new AppNode.Builder("Node1")
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.106"))
                    .build();

            AppElement node2 = new AppNode.Builder("Node2")
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.106"))
                    .build();

            AppElement node3 = new AppNode.Builder("Node3")
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.107"))
                    .build();

            AppElement node4 = new AppNode.Builder("Node4")
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.108"))
                    .build();

            int appI = 1;
            AppElement app1 = new AppNode.Builder("App" + (appI++))
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.109"))
                    .build();

            AppElement app2 = new AppNode.Builder("App" + (appI++))
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.110"))
                    .build();

            AppElement app3 = new AppNode.Builder("App" + (appI++))
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.111"))
                    .build();

            AppElement app4 = new AppNode.Builder("App" + (appI++))
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.112"))
                    .build();

            AppElement app5 = new AppNode.Builder("App" + (appI++))
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.113"))
                    .build();

            AppElement app6 = new AppNode.Builder("App" + (appI++))
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.114"))
                    .build();

            AppElement app7 = new AppNode.Builder("App" + (appI++))
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.115"))
                    .build();

            AppElement app8 = new AppNode.Builder("App" + (appI++))
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.116"))
                    .build();

            AppElement app9 = new AppNode.Builder("App" + (appI++))
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.117"))
                    .build();

            AppElement app10 = new AppNode.Builder("App" + (appI++))
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.118"))
                    .build();

            AppElement app11 = new AppNode.Builder("App" + (appI++))
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.119"))
                    .build();

            AppElement app12 = new AppNode.Builder("App" + (appI++))
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.120"))
                    .build();

            homer.getConnections().add(node1);
            lisa.getConnections().add(node2);
            node1.getConnections().add(bart);
            node2.getConnections().add(meggie);

            bart.getConnections().add(app1);
            bart.getConnections().add(app2);
            bart.getConnections().add(app3);

            meggie.getConnections().add(app4);
            meggie.getConnections().add(app5);
            meggie.getConnections().add(app6);

            node4.getConnections().add(app7);
            node4.getConnections().add(app8);
            node4.getConnections().add(app9);
            
            marge.getConnections().add(app12);
            node3.getConnections().add(app11);
            
            node4.getConnections().add(app10);

            AppElement physicalLayer = new AppLayer.Builder("Physical Machines").build();
            AppElement cloudService = new AppLayer.Builder("Cloud Service").build();
            AppElement virtualLayer = new AppLayer.Builder("Virual Machines").build();
            AppElement appLayer = new AppLayer.Builder("Application").build();

            physicalLayer.getNodes().addAll(homer, lisa, marge);
            cloudService.getNodes().addAll(node1, node2, node3, node4);
            virtualLayer.getNodes().addAll(bart, meggie);
            appLayer.getNodes().addAll(app1, app2, app3, app4, app5, app6, app7, app8, app9, app10, app11, app12);

            state.addNodeData(homer);
            state.addNodeData(lisa);
            state.addNodeData(marge);
            state.addNodeData(bart);
            state.addNodeData(meggie);
            state.addNodeData(node1);
            state.addNodeData(node2);
            state.addNodeData(node3);
            state.addNodeData(node4);
            state.addNodeData(app1);
            state.addNodeData(app2);
            state.addNodeData(app3);
            state.addNodeData(app4);
            state.addNodeData(app5);
            state.addNodeData(app6);
            state.addNodeData(app7);
            state.addNodeData(app8);
            state.addNodeData(app9);
            state.addNodeData(app10);
            state.addNodeData(app11);
            state.addNodeData(app12);

            state.addLayer(physicalLayer);
            state.addLayer(cloudService);
            state.addLayer(virtualLayer);
            state.addLayer(appLayer);

            callback.success(state);
        });

    }
}
