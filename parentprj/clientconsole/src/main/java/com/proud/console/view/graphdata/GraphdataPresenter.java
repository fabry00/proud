package com.proud.console.view.graphdata;

import com.proud.commons.DateUtils;
import com.proud.console.domain.AppMetric;
import com.proud.console.domain.IAppElement;
import com.proud.console.service.appservice.ApplicationService;
import com.proud.console.util.NodeUtil;
import com.proud.console.view.graphdata.toolbar.IToolbarListener;
import com.proud.console.view.graphdata.toolbar.ToolbarPresenter;
import com.proud.console.view.graphdata.toolbar.ToolbarView;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * @author Fabrizio Faustinoni
 */
public class GraphdataPresenter implements Initializable, IToolbarListener {

    private final Logger logger = Logger.getLogger(GraphdataPresenter.class);

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
    private Label labelValues;
    private ToolbarPresenter tbPresenter;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.debug("Initialize");

        initToolbar();
        initChart();
        initChartTooltip();
    }

    @Override
    public void metricSelected(AppMetric metric) {
        removeAllSeries();
        // Fire node nodesSelectedChanged to re-add all the series with the right metric
        nodesSelectedChanged();
        chart.setTitle(metric.getDesc());

    }

    @Override
    public void resetSeriesClicked() {
        // As are observable, deleting the chart series we dalete also the node data
        seriesList.forEach((serie) -> serie.getData().clear());
        chart.requestLayout();
    }

    private void initToolbar() {
        ToolbarView tbView = new ToolbarView();
        AnchorPane tbPane = (AnchorPane) tbView.getView();
        graphToolbarPane.getChildren().add(tbPane);
        NodeUtil util = new NodeUtil();
        util.anchorToPaneLeft(tbPane, 0.0);
        util.anchorToPaneTop(tbPane, 0.0);
        util.anchorToPaneRight(tbPane, 0.0);
        tbPresenter = tbView.getRealPresenter();
        tbPresenter.subscribe(this);
    }

    private void initChart() {
        chart.setLegendSide(Side.LEFT);
        chart.setLegendVisible(true);
        // By default, the NumberAxis determines its range automatically. 
        // We can overrule this behavior by setting the autoRanging property to 
        // false, and by providing values for the lowerBound and the upperBound
        // setAutoRanging(false); setLowerBoud(..

        // To change the format for the data try this: (not tested)

//          final DateAxis xAxis = new DateAxis(); final NumberAxis yAxis = new
//          NumberAxis(); xAxis.setLabel("Date"); yAxis.setLabel("Events");
//
//          final LineChart<Date,Number> lineChart = new LineChart<>(xAxis,
//          yAxis); lineChart.setTitle("Events");
//
//          SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
//
//
//          XYChart.Series<Date,Number> series = new XYChart.Series<>();
//          series.setName("Events this Year"); series.getData().add(new
//          XYChart.Data(dateFormat.parse("11/Jan/2014"), 23));
//
        if (chart.getXAxis() instanceof NumberAxis) {
            // Force if to show or not always the zero element
            ((NumberAxis) chart.getXAxis()).setForceZeroInRange(false);
        }

        chart.setData(seriesList);

        chart.setTitle(tbPresenter.getSelectedMetric().getDesc());
    }

    @Override
    public void nodesSelectedChanged() {
        logger.debug("nodeSelectedChange");
        List<IAppElement> nodeSelected = tbPresenter.getNodesSelected();

        removeSeriesFromChart(nodeSelected);
        addSeriesToChart(nodeSelected);
    }

    private void addSeriesToChart(List<IAppElement> nodeSelected) {
        List<XYChart.Series<Date, Object>> serieToAdd = new ArrayList<>();

        // Check Serie to add
        for (IAppElement node : nodeSelected) {
            boolean toAdd = true;
            for (XYChart.Series<Date, Object> serie : seriesList) {

                if (serie.getName().equals(node.getName())) {
                    toAdd = false;
                    break;
                }
            }
            if (toAdd) {
                // Serie not displayed --> add
                serieToAdd.add(getSerieToShow(node));
            }
        }

        logger.debug("Series to add: " + serieToAdd.size());

        // Add Serie
        seriesList.addAll(serieToAdd);
    }

    private void removeSeriesFromChart(List<IAppElement> nodeSelected) {
        List<Integer> serieToRemove = new ArrayList<>();
        // Check Serie to remove
        if (nodeSelected.isEmpty()) {
            // Delete all displayed series
            removeAllSeries();
        } else {
            int index = 0;
            for (XYChart.Series<Date, Object> serie : seriesList) {
                boolean toRemove = false;
                for (IAppElement node : nodeSelected) {
                    if (serie.getName().equals(node.getName())) {
                        toRemove = true;
                        break;
                    }
                }
                if (!toRemove) {
                    // Serie not displayed --> add
                    serieToRemove.add(index);
                }
                index++;
            }
        }

        // Remove series
        serieToRemove.forEach((i) -> seriesList.remove(seriesList.get(i)));
    }

    private void removeAllSeries() {
        List<Integer> serieToRemove = new ArrayList<>();
        for (int i = 0; i < seriesList.size(); i++) {
            serieToRemove.add(i);
        }
        serieToRemove.forEach((i) -> seriesList.remove(seriesList.get(i)));
    }

    private String getSerieName(IAppElement node) {
        return node.getName();
    }

    private XYChart.Series<Date, Object> getSerieToShow(IAppElement node) {
        XYChart.Series<Date, Object> serie = new XYChart.Series();
        serie.setName(getSerieName(node));
        AppMetric metricSelected = tbPresenter.getSelectedMetric();
        serie.setData(node.getMetric(metricSelected));

        return serie;
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

            return "Date: "+util.formatDate(x) + "  Value: " + String.format("%1$,.2f", y);
        }, mouseLocationInScene));

        //  Tooltip.install(chart, tooltip);
        // TODO show tooltip
        /* ObjectProperty<Point2D> mouseLocationInScene = new SimpleObjectProperty<>();

        Tooltip tooltip = new Tooltip();

        chart.addEventHandler(MouseEvent.MOUSE_MOVED, evt -> {
            if (!tooltip.isShowing()) {
                mouseLocationInScene.set(new Point2D(evt.getSceneX(), evt.getSceneY()));
            }
        });
        

        tooltip.textProperty().bind(Bindings.createStringBinding(() -> {
            if (mouseLocationInScene.isNull().get()) {
                return "";
            }
            double xInXAxis =chart.getXAxis().sceneToLocal(mouseLocationInScene.get()).getX();
            Date x = (Date)chart.getXAxis().getValueForDisplay(xInXAxis);
            double yInYAxis = chart.getYAxis().sceneToLocal(mouseLocationInScene.get()).getY();
            double y = (double)chart.getYAxis().getValueForDisplay(yInYAxis);
            return  x +" "+ y;
        }, mouseLocationInScene));

        Tooltip.install(chart, tooltip);*/
    }

}
