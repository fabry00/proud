package com.proud.console.view.unused.nodeview;

import com.proud.console.domain.AppNode;
import com.proud.console.util.AppImage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Node presenter
 * Created by Fabrizio Faustinoni on 20/09/2016.
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
        nodeLabel.textProperty().set(node.getName());
        nodeImage.setImage(getNodeImage(node));
        node.IsFineProp().addListener((observable, oldValue, newValue) -> {
            nodeImage.setImage(getNodeImage(node));
        });

    }

    /**
     * TODO retrieve the right image
     *
     * @param node the node
     * @return the image
     */
    private Image getNodeImage(AppNode node) {
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
