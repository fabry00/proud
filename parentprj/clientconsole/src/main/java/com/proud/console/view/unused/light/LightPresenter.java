package com.proud.console.view.unused.light;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author airhacks.com
 */
public class LightPresenter implements Initializable {

    @FXML
    Circle light;

    @Inject
    int red;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        light.setFill(Color.rgb(red, 0, 0));
    }

}
