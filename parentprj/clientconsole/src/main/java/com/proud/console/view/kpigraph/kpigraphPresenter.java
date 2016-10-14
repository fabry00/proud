package com.proud.console.view.kpigraph;

import com.proud.commons.DateUtils;
import com.proud.console.domain.AppMetric;
import com.proud.console.domain.IAppElement;
import com.proud.console.service.appservice.ApplicationService;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Side;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.apache.log4j.Logger;
import org.controlsfx.control.StatusBar;

import javax.inject.Inject;
import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Fabrizio Faustinoni
 */
public class kpigraphPresenter implements Initializable {

    private final Logger logger = Logger.getLogger(kpigraphPresenter.class);

    private final ObservableList<XYChart.Series<Date, Object>> seriesList
            = FXCollections.synchronizedObservableList(FXCollections.observableArrayList());
    @Inject
    ApplicationService appService;
    //http://fxexperience.com/2012/01/curve-fitting-and-styling-areachart/
    @FXML
    private AreaChart chart;
    @FXML
    private AnchorPane graphToolbarPane;

    @FXML
    private StatusBar statusBar;

    private Label labelValues;
    private List<IAppElement> nodes = Collections.emptyList();
    private List<AppMetric> metrics = Collections.emptyList();
    private SimpleStringProperty title = new SimpleStringProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.debug("Initialize");

        initStatusBar();
        initChart();
        initChartTooltip();
    }

    private void initStatusBar() {
        setLeftItems();
        setRightItems();
    }

    private void setRightItems() {
        statusBar.textProperty().bind(title);
    }

    private void setLeftItems() {
        labelValues = new Label("");

        HBox rightBox = new HBox();
        rightBox.setSpacing(3);
        rightBox.getChildren().addAll(labelValues, new Separator());
        statusBar.getRightItems().add(rightBox);
    }

    private void initChart() {
        chart.setLegendSide(Side.LEFT);
        chart.setLegendVisible(true);

        if (chart.getXAxis() instanceof NumberAxis) {
            // Force if to show or not always the zero element
            ((NumberAxis) chart.getXAxis()).setForceZeroInRange(false);
        }

        chart.setData(seriesList);

        //chart.setTitle(tbPresenter.getSelectedMetric().getDesc());
    }

    private void updateStatusTitle() {
        title.set("Nodes: " + this.nodes + "   |   Metrics: " + this.metrics);
    }

    private void initChartTooltip() {

        ObjectProperty<Point2D> mouseLocationInScene = new SimpleObjectProperty<>();
        chart.addEventHandler(MouseEvent.MOUSE_MOVED, evt -> mouseLocationInScene.set(new Point2D(evt.getSceneX(), evt.getSceneY())));

        final DateUtils util = new DateUtils();
        labelValues.textProperty().bind(Bindings.createStringBinding(() -> {
            if (mouseLocationInScene.isNull().get()) {
                return "";
            }
            double xInXAxis = chart.getXAxis().sceneToLocal(mouseLocationInScene.get()).getX();
            Date x = (Date) chart.getXAxis().getValueForDisplay(xInXAxis);
            double yInYAxis = chart.getYAxis().sceneToLocal(mouseLocationInScene.get()).getY();
            double y = (double) chart.getYAxis().getValueForDisplay(yInYAxis);

            return "Date: " + util.formatDate(x) + "  Value: " + String.format("%1$,.2f", y);
        }, mouseLocationInScene));
    }

    public void setNodes(List<IAppElement> nodes) {
        this.nodes = nodes;
        updateStatusTitle();
    }

    public void setMetrics(List<AppMetric> metrics) {
        this.metrics = metrics;
        updateStatusTitle();
    }
}
