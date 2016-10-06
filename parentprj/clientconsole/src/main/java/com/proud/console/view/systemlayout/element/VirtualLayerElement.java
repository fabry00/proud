package com.proud.console.view.systemlayout.element;

import com.proud.console.domain.IAppElement;
import com.proud.console.util.view.PannableCanvas;
import com.proud.console.view.systemlayout.ISystemLayoutManager;
import com.proud.console.view.systemlayout.element.layer.LayerElement;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import org.apache.log4j.Logger;

import java.util.Collection;

/**
 *
 * @author Fabrizio Faustinoni
 */
public class VirtualLayerElement extends LayerElement {

    private final Logger logger = Logger.getLogger(VirtualLayerElement.class);

    public VirtualLayerElement(IAppElement layer, Collection<ISystemElement> nodes, double nodeXGap,
                               double layerStartY, ISystemLayoutManager layoutManager, PannableCanvas canvasContainer) {

        super(layer, nodes, nodeXGap, layerStartY, layoutManager, canvasContainer);
    }

    @Override
    public boolean isVirtual() {
        return true;
    }

    @Override
    public Node draw(final double x, final double y) {
        /*Text text = new Text(layer.getName());
        text.getStyleClass().add(LABEL_CLASS);
        panel.setPrefWidth(getWidth(x));
        panel.setPrefHeight(getHeight());
        panel.getStylesheets().add(NODE_CSS);
        panel.getStyleClass().add(NODE_CLASS);        panel.setTranslateX(x);
        panel.setTranslateY(y);

        panel.getChildren().addAll(text);
        panel.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
            new Helper().changeLayour(event, layoutManager, this);
        });

        dragResizer = new DragResizer(panel, Cursor.HAND, layoutManager);
        dragResizer.makeResizable(panel.getPrefWidth(), panel.getPrefHeight());
*/
        logger.debug("draw virtual layer");
        Region panel = (Region)getContainer();
        panel.getStyleClass().clear();
        panel.getStylesheets().clear();
        logger.debug("Styles: "+panel.getStylesheets()+" "+panel.getStyleClass());

        return panel;
    }

}
