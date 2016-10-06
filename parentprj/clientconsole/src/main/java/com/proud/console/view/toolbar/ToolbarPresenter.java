package com.proud.console.view.toolbar;

import com.proud.console.domain.AppMetric;
import com.proud.console.domain.AppNode;
import com.proud.console.domain.IAppElement;
import com.proud.console.service.appservice.ApplicationService;
import com.proud.console.util.view.ComboFxUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import javax.inject.Inject;
import java.net.URL;
import java.util.*;

/**
 * @author Fabrizio Faustinoni
 */
public class ToolbarPresenter implements Initializable {

    private static final int SEPARATOR_PADDING = 15;
    private static final int HBOX_SPACING = 15;
    private static final double NODE_CHOOSER_WIDTH = 200;
    private static final String SHOW_KPIS = "Show KPIs: ";
    private static final String SHOW = "Show";
    private static final String NODE = "Choose Node";
    private static final String METRIC = "Choose Metric";

    private final Set<IToolbarListener> listeners = new HashSet<>();

    @FXML
    private ToolBar toolbar;
    @Inject
    private ApplicationService appService;
    private ComboBox<IAppElement> nodeChooser;
    private ComboBox<AppMetric> metricSelector;
    private HBox comboContainer = new HBox(HBOX_SPACING);


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addInfoLabel();
        addSeparator();

        addNodesChooser();

        addMetricChooser();

        addShowButton();

    }

    private void addInfoLabel() {
        Label label = new Label(SHOW_KPIS);
        toolbar.getItems().addAll(label);
    }

    private void addShowButton() {
        Button buttonShow = new Button(SHOW);
        comboContainer.getChildren().add(buttonShow);

        buttonShow.setOnAction(e -> showKpi());
    }

    private void addSeparator() {
        Separator separator = new Separator(Orientation.VERTICAL);
        separator.setPadding(new Insets(0, SEPARATOR_PADDING, 0, SEPARATOR_PADDING));
        toolbar.getItems().add(separator);

        toolbar.getItems().add(comboContainer);
    }

    public void subscribe(IToolbarListener listener) {
        this.listeners.add(listener);
    }

    public String[] getMetricsList(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }

    /* public void addItem(NodeData node) {
        if (!nodesInComboBox.contains(node)) {
            nodesInComboBox.add(node);
        }
    }*/
    public boolean isChecked(AppNode node) {

        //return nodeChooser.checkModelProperty().get().isChecked(node);
        return false;
    }

    private List<IAppElement> getNodesSelected() {
       /* List<IAppElement> nodesSelected = new ArrayList<>();
        nodesSelected.showKpi(nodeChooser.checkModelProperty().get().getCheckedItems());
        return Collections.unmodifiableList(nodesSelected);*/
        return null;
    }

    public AppMetric getSelectedMetric() {
        //  return AppMetric.valueOf(metricSelector.getSelectionModel().getSelectedItem());
        return null;
    }

    private void addNodesChooser() {

        nodeChooser = new ComboBox<>(appService.getCurrentState().getNodes());
        nodeChooser.setEditable(true);
        nodeChooser.setPrefWidth(NODE_CHOOSER_WIDTH);
        nodeChooser.setPromptText(NODE);
        ComboFxUtil.autoCompleteComboBoxPlus(nodeChooser,
                (typedText, itemToCompare) -> itemToCompare.getName().toLowerCase().contains(typedText.toLowerCase()));

        comboContainer.getChildren().add(nodeChooser);
    }

    private void addMetricChooser() {
        ObservableList<AppMetric> options = FXCollections.observableArrayList(AppMetric.values());
        metricSelector = new ComboBox<>();
        metricSelector.getItems().setAll(options);
        metricSelector.setPromptText(METRIC);
        metricSelector.setPrefWidth(NODE_CHOOSER_WIDTH);
        ComboFxUtil.autoCompleteComboBoxPlus(
                metricSelector,
                (typedText, itemToCompare) -> itemToCompare.toString().toLowerCase().contains(typedText.toLowerCase()));

        comboContainer.getChildren().add(metricSelector);
    }

    private void resetSeries() {
        /*listeners.forEach((listener) -> {
            Platform.runLater(() -> listener.resetSeriesClicked());
        });*/
    }

    private void showKpi() {
        IAppElement node = ComboFxUtil.getComboBoxValue(nodeChooser);
        AppMetric metric = ComboFxUtil.getComboBoxValue(metricSelector);
        listeners.forEach((listener) -> Platform.runLater(() -> listener.showKpi(Collections.singletonList(node), Collections.singletonList(metric))));
    }

    private void removeAll() {
        System.out.println("Event already fired");
        //nodeChooser.checkModelProperty().get().clearChecks();
    }

    private void metricSelected() {
        /*AppMetric metric = AppMetric.valueOf(metricSelector.getSelectionModel().getSelectedItem());
        listeners.forEach((listener) -> {
            Platform.runLater(() -> listener.metricSelected(metric));
        });*/
    }

    private void nodesSelectedChange() {
        /*listeners.forEach((listener) -> {
            Platform.runLater(() -> listener.nodesSelectedChanged());
        });*/
    }

}
