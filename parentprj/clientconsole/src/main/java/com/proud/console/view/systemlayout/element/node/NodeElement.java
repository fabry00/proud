package com.proud.console.view.systemlayout.element.node;

import com.proud.commons.DateUtils;
import com.proud.console.domain.ElementInfo;
import com.proud.console.domain.IAppElement;
import com.proud.console.util.AppImage;
import com.proud.console.util.view.DragContext;
import com.proud.console.util.view.PannableCanvas;
import com.proud.console.view.systemlayout.ISystemLayoutManager;
import com.proud.console.view.systemlayout.element.Connection;
import com.proud.console.view.systemlayout.element.Helper;
import com.proud.console.view.systemlayout.element.ISystemElement;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.util.*;

/**
 * @author Fabrizio Faustinoni
 */
public class NodeElement implements ISystemElement {

    private static final String NODE_CSS = "com/proud/console/view/systemlayout/element/node/node.css";
    private static final String NODE_CLASS = "system-node";
    private static final String SELECTED_CLASS = "system-node-selected";
    private static final int WIDTH = 84;
    private static final int HEIGHT = 100;

    private final IAppElement node;
    private final VBox panel = new VBox();
    private final Map<ISystemElement, Connection> connections = new HashMap<>();
    private final DragContext nodeDragContext = new DragContext();
    private final PannableCanvas canvasContainer;
    private final Helper helper = new Helper();
    private ISystemLayoutManager layoutManager = null;
    private ISystemElement parent = null;
    private boolean isSelected = false;

    public NodeElement(IAppElement node, ISystemLayoutManager layoutManager,
                       PannableCanvas canvasContainer) {
        this.node = node;
        this.layoutManager = layoutManager;
        this.canvasContainer = canvasContainer;
    }

    @Override
    public ISystemElement getParent() {
        return this.parent;
    }

    @Override
    public void setParent(ISystemElement parent) {
        this.parent = parent;
        helper.initParentEvents(parent.getContainer(), panel);
    }

    @Override
    public IAppElement getAppElement() {
        return node;
    }

    @Override
    public boolean isVirtual() {
        return node.isVirtual();
    }

    @Override
    public Node draw(double x, double y) {
        initNode(x, y);
        return panel;
    }

    @Override
    public Node getContainer() {
        return panel;
    }

    @Override
    public ISystemElement clone() {
        ISystemElement nodeElement = new NodeElement(node.clone(), layoutManager, canvasContainer);

        return nodeElement;
    }

    @Override
    public Collection<ISystemElement> getNodes() {
        throw new UnsupportedOperationException("Not supported.");
    }

    private void initNode(double x, double y) {
        panel.setPrefWidth(WIDTH);
        panel.setPrefHeight(HEIGHT);
        panel.getStylesheets().add(NODE_CSS);
        panel.getStyleClass().add(NODE_CLASS);
        panel.setTranslateX(x);
        panel.setTranslateY(y);

        Label nodeName = new Label(node.getName());
        //nodeName.prefHeightProperty().bind(panel.prefHeightProperty());
        nodeName.prefWidthProperty().bind(panel.prefWidthProperty());
        nodeName.setWrapText(true);
        nodeName.setTextAlignment(TextAlignment.CENTER);
        nodeName.setAlignment(Pos.CENTER);

        panel.getChildren().add(nodeName);

        ImageView imgView = new ImageView();
        // TODO check node element image dimension
        /*   imgView.setFitHeight(80);
         imgView.setFitWidth(80);
         imgView.setLayoutX(2);
         imgView.setLayoutY(28);*/
        imgView.pickOnBoundsProperty().setValue(true);
        imgView.setPreserveRatio(true);
        Image img = AppImage.IMG_PC_GREEN_64;
        imgView.setImage(img);
        panel.getChildren().add(imgView);

        initTooltip();

        initEvents();

    }

    @Override
    public void createConnections(List<ISystemElement> relatedElements) {
        relatedElements.stream().forEach((e) -> {
            Connection connection = createConnection(e);
            if (connection != null) {
                connections.put(e, connection);
            }
        });
    }

    @Override
    public Collection<Connection> getConnections() {
        return connections.values();
    }

    @Override
    public String getName() {
        return node.getName();
    }

    private Connection createConnection(ISystemElement relatedElement) {
        if (relatedElement == null) {
            // This mean that the current relatedElement is not displayed, for
            // example if you selected more node and click on the context menu "ZoomIn"
            return null;
        }
        DoubleProperty x = new SimpleDoubleProperty(panel.translateXProperty().get() + 84 / 2);
        DoubleProperty y = new SimpleDoubleProperty(helper.getAnchorY(panel, (Region) relatedElement.getContainer()));

        DoubleProperty x2 = new SimpleDoubleProperty(relatedElement.getContainer().translateXProperty().get() + 84 / 2);
        DoubleProperty y2 = new SimpleDoubleProperty(helper.getAnchorY((Region) relatedElement.getContainer(), panel));

        panel.translateXProperty().addListener((observable, oldValue, newValue) -> {
            // Source node
            y.setValue(helper.getAnchorY(panel, (Region) relatedElement.getContainer()));
            x.setValue(helper.getAnchorX(panel, (Region) relatedElement.getContainer(), newValue));

            // TargetNode
            y2.setValue(helper.getAnchorY((Region) relatedElement.getContainer(), panel));
        });

        relatedElement.getContainer().translateXProperty().addListener((observable, oldValue, newValue) -> {
            // Source node            
            x2.setValue(helper.getAnchorX((Region) relatedElement.getContainer(), panel, newValue));
            y2.setValue(helper.getAnchorY((Region) relatedElement.getContainer(), panel));

            // TargetNode
            y.setValue(helper.getAnchorY(panel, (Region) relatedElement.getContainer()));
        });

        return new Connection(x, y, x2, y2);
    }

    @Override
    public IAppElement.Type getType() {
        return node.getType();
    }

    @Override
    public String toString() {
        return node.getName();
    }

    @Override
    public void selected() {
        panel.getStyleClass().add(SELECTED_CLASS);
        isSelected = true;
        selectConnections();
    }

    @Override
    public void unSelected() {
        panel.getStyleClass().remove(SELECTED_CLASS);
        isSelected = false;
        unSelectConnections();
    }

    private void initTooltip() {
        String sep = System.getProperty("line.separator");
        String tooltipText = "Node info:" + sep;
        tooltipText += "IP Address: ";
        tooltipText += node.getInfo().get(ElementInfo.Type.IP).getValue() + sep;
        tooltipText += "Last KPI received: " + sep;
        tooltipText += new DateUtils().getNow();

        Tooltip tooltip = new Tooltip(tooltipText);

        Tooltip.install(panel, tooltip);
    }

    private void initEvents() {
        ISystemElement element = this;
        panel.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
            helper.onMousePressedNode(event, layoutManager, nodeDragContext);
        });
        panel.addEventFilter(MouseEvent.MOUSE_DRAGGED, (MouseEvent event) -> {
            helper.onMouseDraggedNode(event, layoutManager, canvasContainer, this, nodeDragContext);
        });
        panel.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            if (!event.getButton().equals(MouseButton.PRIMARY)) {
                return;
            }

            if (!helper.showKpiLayout(event, layoutManager, node.getName(), Arrays.asList(element))) {
                // Layout not changed --> add node as selected
                if (!isSelected) {
                    layoutManager.addSelectedNode(element);
                } else {
                    layoutManager.removeSelectedNode(element);
                }
            }

        });
    }

    private void selectConnections() {
        Color color = Connection.getRandomColor();
        System.out.println("##########COLOR::: " + color.toString());
        for (Map.Entry<ISystemElement, Connection> entry : connections.entrySet()) {
            entry.getValue().selected(color);
        }

    }

    private void unSelectConnections() {
        for (Map.Entry<ISystemElement, Connection> entry : connections.entrySet()) {
            entry.getValue().unSelected();
        }
    }


}
