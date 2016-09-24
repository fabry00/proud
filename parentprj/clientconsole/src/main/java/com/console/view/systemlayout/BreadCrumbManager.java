package com.console.view.systemlayout;

import com.console.view.systemlayout.element.ISystemElement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import org.controlsfx.control.BreadCrumbBar;

/**
 *
 * @author fabry
 */
public class BreadCrumbManager {

    private final static String FIRST_LAYOUT = "System";
    private static final String BASE_LAYOUT_NAME = "Layout";

    private final BreadCrumbBar crumbBar;
    private final TreeItem<String> rootTree;
    private final ISystemLayoutManager layoutManager;
    private final Map<String, TreeItem<String>> layoutsName = new HashMap<>();

    public BreadCrumbManager(BreadCrumbBar crumbBar, ISystemLayoutManager layoutManager) {
        this.crumbBar = crumbBar;
        this.layoutManager = layoutManager;
        this.rootTree = new TreeItem<>(FIRST_LAYOUT);

    }

    public void initCrumbar() {
        //http://stackoverflow.com/questions/30931401/how-to-use-breadcrumbbar-in-controlsfx-javafx-8
        /*TreeItem<String> item1 = new TreeItem<>("Item 1");
        TreeItem<String> item11 = new TreeItem<>("Item 1.1");
        TreeItem<String> item12 = new TreeItem<String>("Item 1.2");

        item1.getChildren().addAll(item11, item12);

        TreeItem<String> item2 = new TreeItem<>("Item 2");

        rootTree.getChildren().addAll(item1, item2);*/
        crumbBar.selectedCrumbProperty().set(rootTree);
        crumbBar.setAutoNavigationEnabled(false);
        crumbBar.setOnCrumbAction(new EventHandler<BreadCrumbBar.BreadCrumbActionEvent<String>>() {
            @Override
            public void handle(BreadCrumbBar.BreadCrumbActionEvent<String> bae) {
                System.out.println("BreadCrumbActionEvent");
            }
        });

    }

    public void selectLayout(List<ISystemElement> elementsToShow) {
        String layoutName = getNewLayoutName(elementsToShow);
        selectItemLayout(elementsToShow, layoutName);
    }

    private void selectItemLayout(List<ISystemElement> elementsToShow, String layoutName) {
        TreeItem<String> item;

        if (!layoutsName.containsKey(layoutName)) {
            item = new TreeItem<>(layoutName);
            layoutsName.put(layoutName, item);
            // TreeItem<String> currItem = (TreeItem<String>) crumbBar.selectedCrumbProperty().get();
            TreeItem<String> parent = getParent(elementsToShow);
            parent.getChildren().add(item);
        } else {
            item = layoutsName.get(layoutName);
        }

        crumbBar.selectedCrumbProperty().set(item);
    }

    private String getNewLayoutName(List<ISystemElement> elementsToShow) {
        if (elementsToShow.size() == 1) {
            return elementsToShow.get(0).getName();
        }
        return BASE_LAYOUT_NAME + " - " + rootTree.getChildren().size();
    }

    private TreeItem<String> getParent(List<ISystemElement> elementsToShow) {

        TreeItem<String> parentElement = rootTree;

        String finalParentLayoutName = null;
        for (ISystemElement element : elementsToShow) {
            ISystemElement parent = element.getParent();
            if (parent == null) {
                continue;
            }
            String parentLayoutName = getNewLayoutName(Arrays.asList(parent));
            if (layoutsName.containsKey(parentLayoutName)) {
                if (finalParentLayoutName != null
                        && !finalParentLayoutName.equals(parentLayoutName)) {
                    //Different layout
                    finalParentLayoutName = null;
                } else {
                    parentElement = layoutsName.get(parentLayoutName);
                    finalParentLayoutName = null;
                }
            } else {
                // Clicked on a node without clicking before on the parent layer -->
                // this mean that The parent tree has not been creted yet
                // TODO this should be recoursive
                parentElement = new TreeItem<>(getNewLayoutName(Arrays.asList(parent)));
                rootTree.getChildren().add(parentElement);
            }
        }
        return parentElement;
    }

}
