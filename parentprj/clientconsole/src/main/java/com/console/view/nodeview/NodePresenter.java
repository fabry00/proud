package com.console.view.nodeview;

import com.console.domain.AppNode;
import com.console.util.AppImage;
import com.console.util.AppPath;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by exfaff on 20/09/2016.
 */
public class NodePresenter implements Initializable {

    @FXML
    Label nodeLabel;

    @FXML
    ImageView nodeImage;

    private AppNode node;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setNode(AppNode node) {
        this.node = node;
        nodeLabel.textProperty().set(node.getNode());
        nodeImage.setImage(getNodeImage(node));
        node.IsFineProp().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                System.out.println("############################## CHANGEDDDDDDDDDDDD");
                nodeImage.setImage(getNodeImage(node));
            }
        });

    }

    public Image getNodeImage(AppNode node) {
        /* if(node.IsFine()){
            return FINE;
        }
        if(node.AnomalyDetected()) {
            return WARNING;
        }
        if(node.FailureDetected()) {
            return FAIL;
        }*/

        return AppImage.IMG_PC_GREEN_64;
    }
}
