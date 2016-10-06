package com.proud.console.view.dashboard;

import com.proud.commons.AppHelper;
import com.proud.commons.ConfigUtils;
import com.proud.console.service.appservice.ApplicationService;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.util.Calendar;

/**
 * About dialog
 * Created by fabry on 02/10/2016.
 */
class About {
    private static final String ABOUT = "About";
    private static final String VERSION = "Version: ";
    private static final String JRE = "JRE: ";
    private static final String JVM = "JVM: ";
    private static final int START_YEAR = 2016;
    private static final String COPYRIGHT = "Copyright: ";
    private static final String RIGHT_RESERVED = "All rights reserved";

    public void show(ApplicationService appService) {
        final String appVersion = appService.getAppConfigs().getProperty(ConfigUtils.KEY_VERSION);
        AppHelper helper = new AppHelper();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Label logo = new Label("PrOud");
        logo.getStylesheets().add("com/proud/console/view/dashboard/logo.css");
        logo.getStyleClass().add("aboutLogoLabel");
        logo.setPrefWidth(300);
        logo.setPrefHeight(80);
        logo.setAlignment(Pos.CENTER);
        alert.setGraphic(logo);
        alert.setTitle(ABOUT);
        String sep = System.getProperty("line.separator");
        String content = VERSION + appVersion + sep;
        content += JRE + helper.getJRE() + sep;
        content += JVM + helper.getJVM() + sep;
        alert.setHeaderText(content);

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        String copy = START_YEAR + "";
        if (currentYear != START_YEAR) {
            copy += " - " + currentYear;
        }
        alert.setContentText(COPYRIGHT + copy + " " + RIGHT_RESERVED);

        alert.showAndWait();
    }
}
