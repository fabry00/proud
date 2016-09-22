package com.console.view.systemlayout.element;

import com.console.domain.AppElement;
import com.console.util.view.NodeGestures;
import java.util.Collection;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 *
 * @author exfaff
 */
public class LayerElement implements SystemElement {

    private static final String NODE_CSS = "com/console/view/systemlayout/element/layer.css";
    private static final String NODE_CLASS = "system-layer";
    private static final String LABEL_CLASS = "system-layer-text";
    private static final double Y_PADDING = 20;

    private final AppElement layer;
    private final Collection<SystemElement> nodes;
    // private final StackPane panel = new StackPane();
    private final VBox panel = new VBox();
    private final double nodeXGap;
    private final double layerStartY;

    public LayerElement(AppElement layer, Collection<SystemElement> nodes,
            double nodeXGap, double layerStartY) {
        this.layer = layer;
        this.nodes = nodes;
        this.nodeXGap = nodeXGap;
        this.layerStartY = layerStartY;
    }

    @Override
    public Node draw(double x, double y, NodeGestures nodeGestures) {
        Text text = new Text(layer.getName());
        text.getStyleClass().add(LABEL_CLASS);
        panel.setPrefWidth(getWidth());
        panel.setPrefHeight(getHeight());
        panel.getStylesheets().add(NODE_CSS);
        panel.getStyleClass().add(NODE_CLASS);
        panel.setTranslateX(x);
        panel.setTranslateY(y);

        panel.getChildren().addAll(text);

        panel.addEventFilter(MouseEvent.MOUSE_CLICKED, nodeGestures.getOnLayerClickedEventHandler());
        return panel;
    }

    @Override
    public AppElement.Type getType() {
        return AppElement.Type.Layer;
    }

    @Override
    public void createConnections(List<SystemElement> relatedElements) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Line> getConnections() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Node getContainer() {
        return panel;
    }

    private double getWidth() {
        double width = 0;

        width = nodes.stream().map((element)
                -> ((Region) element.getContainer()).getPrefWidth()).reduce(width, (accumulator, _item)
                -> accumulator + _item + nodeXGap/ 2);

        // TODO retreive the real layer with
        return width - (nodeXGap);
    }

    private double getHeight() {
        double max_height = Integer.MIN_VALUE;
        for (SystemElement element : nodes) {
            double elementH = ((Region) element.getContainer()).getPrefHeight();
            if (elementH > max_height) {
                max_height = elementH;
            }
        }
        return max_height + layerStartY + Y_PADDING;
    }

}
