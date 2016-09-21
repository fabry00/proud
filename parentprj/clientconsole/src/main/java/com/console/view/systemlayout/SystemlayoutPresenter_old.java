package com.console.view.systemlayout;

import com.console.domain.AppNode;
import com.console.service.appservice.ApplicationService;
import com.console.view.nodeview.NodeView;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;

import javax.inject.Inject;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by exfaff on 20/09/2016.
 */
public class SystemlayoutPresenter_old implements Initializable {

    @FXML
    FlowPane systemPane;

    @Inject
    private ApplicationService appService;


    private Set<Integer> nodeAdded = new HashSet<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {




        /*appService.getCurrentState().getNodes().addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(Change<? extends Node> c) {
                while(c.next()){
                    if(c.wasAdded()){
                        addNode(c.getAddedSubList());
                    }
                }
            }
        });*/
    }

    /*private void addNode(List<? extends Node> c) {
        System.out.println("Added nodes: "+c);
        c.forEach((node) -> {
            if(nodeAdded.contains(node.hashCode())) {
                return;
            }
            System.out.println(node.hashCode());
            NodeView nodeview = new NodeView();
            nodeview.getRealPresenter().setNode(node);
            systemPane.getChildren().add(nodeview.getView());
            nodeAdded.add(node.hashCode());
        });

    }*/
}
