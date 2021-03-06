package com.proud.console.view.systemlayout.element.layer;

import com.proud.console.domain.IAppElement;
import com.proud.console.util.view.DragContext;
import com.proud.console.util.view.DragResizer;
import com.proud.console.util.view.PannableCanvas;
import com.proud.console.view.systemlayout.ISystemLayoutManager;
import com.proud.console.view.systemlayout.element.Connection;
import com.proud.console.view.systemlayout.element.Helper;
import com.proud.console.view.systemlayout.element.ISystemElement;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Fabrizio Faustinoni
 */
public class LayerElement implements ISystemElement {

    private static final String NODE_CSS = "com/proud/console/view/systemlayout/element/layer/layer.css";
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
    private final Helper helper = new Helper();
    private final DragContext nodeDragContext = new DragContext();
    private final PannableCanvas canvasContainer;
    private ISystemElement parent = null;
    private DragResizer dragResizer;

    public LayerElement(IAppElement layer, Collection<ISystemElement> nodes, double nodeXGap,
                        double layerStartY, ISystemLayoutManager layoutManager, PannableCanvas canvasContainer) {
        this.layer = layer;
        this.nodes = nodes;
        this.nodeXGap = nodeXGap;
        this.layerStartY = layerStartY;
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
    public boolean isVirtual() {
        return false;
    }

    @Override
    public IAppElement getAppElement() {
        return layer;
    }

    @Override
    public Node draw(final double x, final double y) {
        logger.trace("Draw layer element");
        Text text = new Text(layer.getName());
        text.getStyleClass().add(LABEL_CLASS);
        panel.setPrefWidth(getWidth(x));
        panel.setPrefHeight(getHeight());
        panel.getStylesheets().add(NODE_CSS);
        panel.getStyleClass().add(NODE_CLASS);
        panel.setTranslateX(x);
        panel.setTranslateY(y);

        panel.getChildren().addAll(text);

        dragResizer = new DragResizer(panel, Cursor.HAND, layoutManager);
        dragResizer.makeResizable(panel.getPrefWidth(), panel.getPrefHeight());

        initEvents();
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
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Connection> getConnections() {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Node getContainer() {
        return panel;
    }

    @Override
    public void selected() {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void unSelected() {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ISystemElement clone() {
        ISystemElement layer = new LayerElement(this.layer.clone(), new ArrayList<>(nodes), nodeXGap,
                layerStartY, layoutManager, canvasContainer);

        return layer;
    }

    @Override
    public Collection<ISystemElement> getNodes() {
        return nodes;
    }

    private double getWidth(double x) {
        // TDO clean code
        /*double width = 0;

         width = nodes.stream().map((element)
         -> ((Region) element.getContainer()).getPrefWidth()).reduce(width, (accumulator, _item)
         -> accumulator + _item + nodeXGap / 2);
         //return width - (nodeXGap / 2);
         */
        double lastX = 0;
        for (ISystemElement node : nodes) {
            if (((Region) node.getContainer()).getLayoutX() > lastX) {
                lastX = ((Region) node.getContainer()).getLayoutX();
            }
        }

        return (lastX - x) + nodeXGap * nodes.size() - (nodeXGap / 5);

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

    private void initEvents() {
        panel.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            new Helper().changeLayour(event, layoutManager, this);
        });

        panel.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {

            if (dragResizer.isResizing(event)) {
                return;
            }

            helper.onMousePressedNode(event, layoutManager, nodeDragContext);
        });
        panel.addEventFilter(MouseEvent.MOUSE_DRAGGED, (MouseEvent event) -> {

            if (dragResizer.isResizing(event)) {
                return;
            }

            helper.onMouseDraggedNode(event, layoutManager, canvasContainer, this, nodeDragContext);
        });
    }

}
