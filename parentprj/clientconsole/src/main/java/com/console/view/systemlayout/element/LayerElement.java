package com.console.view.systemlayout.element;

import com.console.util.view.NodeGestures;
import java.util.Collection;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import org.apache.log4j.Logger;
import com.console.domain.IAppElement;
import com.console.view.systemlayout.ISystemLayoutManager;
import com.console.view.systemlayout.SystemLayoutManager;
import javax.inject.Inject;

/**
 *
 * @author exfaff
 */
public class LayerElement implements ISystemElement {

    private static final String NODE_CSS = "com/console/view/systemlayout/element/layer.css";
    private static final String NODE_CLASS = "system-layer";
    private static final String LABEL_CLASS = "system-layer-text";
    private static final double Y_PADDING = 20;

    private final Logger logger = Logger.getLogger(LayerElement.class);

    private final IAppElement layer;
    private final Collection<ISystemElement> nodes;
    // private final StackPane panel = new StackPane();
    private final VBox panel = new VBox();
    private final double nodeXGap;
    private final double layerStartY;
    private final ISystemLayoutManager layoutManager;

    public LayerElement(IAppElement layer, Collection<ISystemElement> nodes,
            double nodeXGap, double layerStartY, ISystemLayoutManager layoutManager) {
        this.layer = layer;
        this.nodes = nodes;
        this.nodeXGap = nodeXGap;
        this.layerStartY = layerStartY;
        this.layoutManager = layoutManager;
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

        panel.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {

            if (!event.getButton().equals(MouseButton.PRIMARY) || event.getClickCount() < 2) {
                return;
            }
            layoutManager.changeLayout(this);
        });
        return panel;
    }

    @Override
    public IAppElement.Type getType() {
        return IAppElement.Type.Layer;
    }

    @Override
    public String getName() {
        return layer.getName();
    }

    @Override
    public void createConnections(List<ISystemElement> relatedElements) {
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
                -> accumulator + _item + nodeXGap / 2);

        // TODO retreive the real layer with
        return width /*- (nodeXGap)*/;
    }

    private double getHeight() {
        double max_height = Integer.MIN_VALUE;
        for (ISystemElement element : nodes) {
            double elementH = ((Region) element.getContainer()).getPrefHeight();
            if (elementH > max_height) {
                max_height = elementH;
            }
        }
        return max_height + layerStartY + Y_PADDING;
    }

}
