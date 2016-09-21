package com.console.view.systemlayout;

import com.console.domain.AppNode;
import com.console.domain.AppState;
import com.console.domain.IAppStateListener;
import com.console.domain.State;
import com.console.service.appservice.ApplicationService;
import com.console.util.AppImage;
import com.console.util.NodeUtil;
import com.console.util.view.NodeGestures;
import com.console.util.view.PannableCanvas;
import com.console.util.view.SceneGestures;
import com.console.view.nodeview.NodeView;
import com.console.view.systemlayout.element.NodeElement;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.ListSelectionView;
import org.controlsfx.control.textfield.TextFields;

import javax.inject.Inject;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Created by exfaff on 20/09/2016.
 */
public class SystemlayoutPresenter implements Initializable, IAppStateListener {

    @FXML
    ScrollPane systemPane;

    @Inject
    private ApplicationService appService;

    private PannableCanvas canvas;
    private NodeGestures nodeGestures;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /* DoubleProperty startX = new SimpleDoubleProperty(100);
        DoubleProperty startY = new SimpleDoubleProperty(100);
        DoubleProperty endX   = new SimpleDoubleProperty(300);
        DoubleProperty endY   = new SimpleDoubleProperty(200);

        Anchor start    = new Anchor(Color.PALEGREEN, startX, startY);
        Anchor end      = new Anchor(Color.TOMATO,    endX,   endY);

        Line line = new BoundLine(startX, startY, endX, endY);

        systemPane.getChildren().add(start);
        systemPane.getChildren().add(end);
        systemPane.getChildren().add(line);*/
//        Group group = new Group();
//
//        // create canvas
//        PannableCanvas canvas = new PannableCanvas(systemPane.getWidth(),systemPane.getHeight());
//
//        // we don't want the canvas on the top/left in this example => just
//        // translate it a bit
//        canvas.setTranslateX(0);
//        canvas.setTranslateY(0);
//
//        // create sample nodes which can be dragged
//        NodeGestures nodeGestures = new NodeGestures(canvas);
//
//        Label label1 = new Label("Draggable node 1");
//        label1.setTranslateX(10);
//        label1.setTranslateY(10);
//        label1.addEventFilter(MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
//        label1.addEventFilter(MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());
//
//        Label label2 = new Label("Draggable node 2");
//        label2.setTranslateX(100);
//        label2.setTranslateY(100);
//        label2.addEventFilter(MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
//        label2.addEventFilter(MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());
//
//        Label label3 = new Label("Draggable node 3");
//        label3.setTranslateX(200);
//        label3.setTranslateY(200);
//        label3.addEventFilter(MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
//        label3.addEventFilter(MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());
//
//        Circle circle1 = new Circle(300, 300, 50);
//        circle1.setStroke(Color.ORANGE);
//        circle1.setFill(Color.ORANGE.deriveColor(1, 1, 1, 0.5));
//        circle1.addEventFilter(MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
//        circle1.addEventFilter(MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());
//
//        Rectangle rect1 = new Rectangle(100, 100);
//        rect1.setTranslateX(450);
//        rect1.setTranslateY(450);
//        rect1.setStroke(Color.BLUE);
//        rect1.setFill(Color.BLUE.deriveColor(1, 1, 1, 0.5));
//        rect1.addEventFilter(MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
//        rect1.addEventFilter(MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());
//
//
//        DoubleProperty startX = new SimpleDoubleProperty(100);
//        DoubleProperty startY = new SimpleDoubleProperty(100);
//        DoubleProperty endX   = new SimpleDoubleProperty(300);
//        DoubleProperty endY   = new SimpleDoubleProperty(200);
//
//        Anchor start    = new Anchor(Color.PALEGREEN, startX, startY);
//        Anchor end      = new Anchor(Color.TOMATO,    endX,   endY);
//
//        Line line = new BoundLine(startX, startY, endX, endY);
//
//      //  NodeView nodeV = new NodeView();
//       // nodeV.getView().addEventFilter(MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());
//        //canvas.getChildren().addAll(label1, label2, label3, circle1, rect1,start,end,line, nodeV.getView());
//
//       /* Pane p = new Pane();
//        p.setPrefWidth(80);
//        p.setPrefHeight(80);
//        p.getChildren().add(new Label("homer"));
//        p.getChildren().add(new Label("image"));
//        p.addEventFilter(MouseEvent.MOUSE_PRESSED, nodeGestures.getOnMousePressedEventHandler());
//        p.addEventFilter(MouseEvent.MOUSE_DRAGGED, nodeGestures.getOnMouseDraggedEventHandler());*/
///////////////////////////////////////////// NodeTEST
//
//
//        VBox p2 = getNode("AAAAAAA",nodeGestures,380,350);
//      
//        VBox p3 = getNode("BBBBBBB",nodeGestures,500,400);
//
//      //  p2.translateXProperty().bind(p2.ce);
//        DoubleProperty x = new SimpleDoubleProperty(p2.translateXProperty().get() +84 /2);
//        DoubleProperty y = new SimpleDoubleProperty(p2.translateYProperty().get());
//        p2.translateXProperty().addListener((observable, oldValue, newValue) -> {
//
//            x.setValue((double) newValue + p2.widthProperty().get() /2);
//            y.setValue(p2.translateYProperty().get() +p2.heightProperty().get() );
//            /*if(p2.translateYProperty().get() < p3.translateYProperty().get()) {
//                //x.setValue((double) newValue + p2.widthProperty().get() /2);
//                y.setValue(p2.translateYProperty().get() +p2.heightProperty().get() );
//            } else {
//                y.setValue(p2.translateYProperty().get());
//            }
//            x.setValue((double) newValue + p2.widthProperty().get() /2);*/
//        });
//
//        DoubleProperty x2 = new SimpleDoubleProperty(p3.translateXProperty().get() +84 /2);
//        p3.translateXProperty().addListener((observable, oldValue, newValue) -> {
//            x2.setValue((double) newValue + p3.widthProperty().get() /2);
//        });
//
//        Line line2 = new BoundLine(x, y,
//                                   x2, p3.translateYProperty());
//
////////////////////////////////////////////////////////////////////////
//
//
//        ComboBox<String> ccc = new ComboBox<>();
//        ccc.getItems().addAll("AAAA","BBBBB","CCCCCC");
//        ccc.setEditable(true);
//        TextFields.bindAutoCompletion(ccc.getEditor(),ccc.getItems());
//
//
//      
//
//
//        canvas.getChildren().addAll(label1, label2, label3, circle1, rect1,start,end,line, p2,p3, line2,ccc);
//
//        group.getChildren().add(canvas);
//
//        // create scene which can be dragged and zoomed
//        systemPane.setContent(group);
//        systemPane.setPannable(true);// enable scroll with mouse
//        SceneGestures sceneGestures = new SceneGestures(canvas);
//        systemPane.addEventFilter(MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
//        systemPane.addEventFilter(MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
//        systemPane.addEventFilter(ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());
        Group group = new Group();
        canvas = new PannableCanvas(systemPane.getWidth(), systemPane.getHeight());
        // we don't want the canvas on the top/left in this example => just
        // translate it a bit
        canvas.setTranslateX(0);
        canvas.setTranslateY(0);

        // create sample nodes which can be dragged
        nodeGestures = new NodeGestures(canvas);

        group.getChildren().add(canvas);
        systemPane.setContent(group);
        systemPane.setPannable(true);// enable scroll with mouse
        SceneGestures sceneGestures = new SceneGestures(canvas);
        systemPane.addEventFilter(MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        systemPane.addEventFilter(MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        systemPane.addEventFilter(ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());

        appService.subscribeToState(this, State.STARTED);

    }

    @Override
    public void AppStateChanged(AppState oldState, AppState currentState) {
        System.out.println("################## APP STARTED " + currentState.getNodes().size());

        SystemLayoutFactory factory = new SystemLayoutFactory();
        factory.draw(canvas, nodeGestures, currentState);
    }

    class BoundLine extends Line {

        BoundLine(DoubleProperty startX, DoubleProperty startY, DoubleProperty endX, DoubleProperty endY) {
            startXProperty().bind(startX);
            startYProperty().bind(startY);
            endXProperty().bind(endX);
            endYProperty().bind(endY);
            setStrokeWidth(2);
            setStroke(Color.GRAY.deriveColor(0, 1, 1, 0.5));
            setStrokeLineCap(StrokeLineCap.BUTT);
            getStrokeDashArray().setAll(10.0, 5.0);
            setMouseTransparent(true);
        }
    }

    // a draggable anchor displayed around a point.
    class Anchor extends Circle {

        Anchor(Color color, DoubleProperty x, DoubleProperty y) {
            super(x.get(), y.get(), 10);
            setFill(color.deriveColor(1, 1, 1, 0.5));
            setStroke(color);
            setStrokeWidth(2);
            setStrokeType(StrokeType.OUTSIDE);

            x.bind(centerXProperty());
            y.bind(centerYProperty());
            enableDrag();
        }

        // make a node movable by dragging it around with the mouse.
        private void enableDrag() {
            final Delta dragDelta = new Delta();
            setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    // record a delta distance for the drag and drop operation.
                    dragDelta.x = getCenterX() - mouseEvent.getX();
                    dragDelta.y = getCenterY() - mouseEvent.getY();
                    getScene().setCursor(Cursor.MOVE);
                }
            });
            setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    getScene().setCursor(Cursor.HAND);
                }
            });
            setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    double newX = mouseEvent.getX() + dragDelta.x;
                    if (newX > 0 && newX < getScene().getWidth()) {
                        setCenterX(newX);
                    }
                    double newY = mouseEvent.getY() + dragDelta.y;
                    if (newY > 0 && newY < getScene().getHeight()) {
                        setCenterY(newY);
                    }
                }
            });
            setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (!mouseEvent.isPrimaryButtonDown()) {
                        getScene().setCursor(Cursor.HAND);
                    }
                }
            });
            setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (!mouseEvent.isPrimaryButtonDown()) {
                        getScene().setCursor(Cursor.DEFAULT);
                    }
                }
            });
        }

        // records relative x and y co-ordinates.
        private class Delta {

            double x, y;
        }
    }

    class Center {

        private ReadOnlyDoubleWrapper centerX = new ReadOnlyDoubleWrapper();
        private ReadOnlyDoubleWrapper centerY = new ReadOnlyDoubleWrapper();

        public Center(Node node) {
            calcCenter(node.getBoundsInParent());
            node.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
                @Override
                public void changed(
                        ObservableValue<? extends Bounds> observableValue,
                        Bounds oldBounds,
                        Bounds bounds
                ) {
                    calcCenter(bounds);
                }
            });
        }

        private void calcCenter(Bounds bounds) {
            centerX.set(bounds.getMinX() + bounds.getWidth() / 2);
            centerY.set(bounds.getMinY() + bounds.getHeight() / 2);
        }

        ReadOnlyDoubleProperty centerXProperty() {
            return centerX.getReadOnlyProperty();
        }

        ReadOnlyDoubleProperty centerYProperty() {
            return centerY.getReadOnlyProperty();
        }
    }

}
