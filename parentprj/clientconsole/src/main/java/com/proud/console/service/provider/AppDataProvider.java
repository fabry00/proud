package com.proud.console.service.provider;

import com.proud.console.domain.*;
import com.proud.domain.PredictionType;
import javafx.application.Platform;

/**
 * @author Fabrizio Faustinoni
 */
public class AppDataProvider implements IDataProvider {

    /**
     * TODO retrieve from server
     *
     * @param callback The callback
     */
    public void getSystemState(final ICallback callback) {

        //Platform.runLater(() -> {
            AppState state = new AppState.Builder(null)
                    .failurePrediction(PredictionType.DETECTED)
                    .build();

            IAppElement homer = new AppNode.Builder("Homer")
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.101"))
                    .build();

            IAppElement lisa = new AppNode.Builder("Lisa")
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.102"))
                    .build();

            IAppElement marge = new AppNode.Builder("Marge")
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.103"))
                    .build();

            IAppElement bart = new AppNode.Builder("Bart")
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.104"))
                    .build();

            IAppElement meggie = new AppNode.Builder("Meggie")
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.105"))
                    .build();

            IAppElement node1 = new AppNode.Builder("Node1")
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.106"))
                    .build();

            IAppElement node2 = new AppNode.Builder("Node2")
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.106"))
                    .build();

            IAppElement node3 = new AppNode.Builder("Node3")
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.107"))
                    .build();

            IAppElement node4 = new AppNode.Builder("Node4")
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.108"))
                    .build();

            int appI = 1;
            IAppElement app1 = new AppNode.Builder("App" + (appI++))
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.109"))
                    .build();

            IAppElement app2 = new AppNode.Builder("App" + (appI++))
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.110"))
                    .build();

            IAppElement app3 = new AppNode.Builder("App" + (appI++))
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.111"))
                    .build();

            IAppElement app4 = new AppNode.Builder("App" + (appI++))
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.112"))
                    .build();

            IAppElement app5 = new AppNode.Builder("App" + (appI++))
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.113"))
                    .build();

            IAppElement app6 = new AppNode.Builder("App" + (appI++))
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.114"))
                    .build();

            IAppElement app7 = new AppNode.Builder("App" + (appI++))
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.115"))
                    .build();

            IAppElement app8 = new AppNode.Builder("App" + (appI++))
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.116"))
                    .build();

            IAppElement app9 = new AppNode.Builder("App" + (appI++))
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.117"))
                    .build();

            IAppElement app10 = new AppNode.Builder("App" + (appI++))
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.118"))
                    .build();

            IAppElement app11 = new AppNode.Builder("App" + (appI++))
                    .withInfo(new ElementInfo(ElementInfo.Type.IP, "192.168.1.119"))
                    .build();

            IAppElement app12 = new AppNode.Builder("App" + (appI++))
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

            IAppElement physicalLayer = new AppLayer.Builder("Physical Machines").build();
            IAppElement cloudService = new AppLayer.Builder("Cloud Service").build();
            IAppElement virtualLayer = new AppLayer.Builder("Virtual Machines").build();
            IAppElement appLayer = new AppLayer.Builder("Application").build();

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

            state.addLayer(appLayer);
            state.addLayer(virtualLayer);
            state.addLayer(cloudService);
            state.addLayer(physicalLayer);

            callback.success(state);
        //});

    }
}
