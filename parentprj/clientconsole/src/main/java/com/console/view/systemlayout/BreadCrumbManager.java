package com.console.view.systemlayout;

import com.console.service.appservice.ApplicationService;
import com.console.view.systemlayout.element.ISystemElement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import org.apache.log4j.Logger;
import org.controlsfx.control.BreadCrumbBar;

/**
 * TODO This class could be improved --> the logic could be based on the layers
 * list saved in appService
 *
 * @author fabry
 */
public class BreadCrumbManager {

    private static final String FIRST_LAYOUT = "System";
    private static final String BASE_LAYOUT_NAME = "Layout";

    private final Logger logger = Logger.getLogger(BreadCrumbManager.class);

    private final BreadCrumbBar crumbBar;
    private final TreeItem<String> rootTree;
    private final ISystemLayoutManager layoutManager;
    private final Map<String, TreeItem<String>> layoutsName = new HashMap<>();
    private final Map<String, List<ISystemElement>> layoutElements = new HashMap<>();
    private final ApplicationService appService;

    public BreadCrumbManager(BreadCrumbBar crumbBar, ISystemLayoutManager layoutManager,
            ApplicationService appService) {
        this.crumbBar = crumbBar;
        this.layoutManager = layoutManager;
        this.rootTree = new TreeItem<>(FIRST_LAYOUT);
        this.appService = appService;
    }

    public void initCrumbar() {
        //http://stackoverflow.com/questions/30931401/how-to-use-breadcrumbbar-in-controlsfx-javafx-8
        /*TreeItem<String> item1 = new TreeItem<>("Item 1");
        TreeItem<String> item11 = new TreeItem<>("Item 1.1");
        TreeItem<String> item12 = new TreeItem<String>("Item 1.2");

        item1.getChildren().addAll(item11, item12);

        TreeItem<String> item2 = new TreeItem<>("Item 2");

        rootTree.getChildren().addAll(item1, item2);*/

        crumbBar.setAutoNavigationEnabled(false);
        crumbBar.setOnCrumbAction(new EventHandler<BreadCrumbBar.BreadCrumbActionEvent<String>>() {
            @Override
            public void handle(BreadCrumbBar.BreadCrumbActionEvent<String> bae) {
                String layoutSelected = bae.getSelectedCrumb().getValue();
                logger.debug("Load layout: " + layoutSelected);

                if (layoutElements.containsKey(layoutSelected)) {
                    layoutManager.changeLayout(layoutElements.get(layoutSelected));
                    return;
                }

                logger.error("Layout not found: " + layoutSelected);

            }
        });

        layoutsName.put(FIRST_LAYOUT, rootTree);
        crumbBar.selectedCrumbProperty().set(rootTree);

    }

    public void setRootElements(List<ISystemElement> elementsToShow) {
        logger.debug("setRootElements");
        //selectItemLayout(elementsToShow, FIRST_LAYOUT);
        layoutElements.put(FIRST_LAYOUT, elementsToShow);
    }

    public void selectLayout(List<ISystemElement> elementsToShow) {
        String layoutName = getNewLayoutName(elementsToShow);
        logger.debug("Selected layout: " + layoutName);
        selectItemLayout(elementsToShow, layoutName);
        layoutElements.put(layoutName, elementsToShow);
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
        if (elementsToShow.equals(layoutElements.get(FIRST_LAYOUT))) {
            return FIRST_LAYOUT;
        }
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
                // this mean that The parent tree has not been created yet
                // TODO this should be recoursive

                //String parentLayoutName = getNewLayoutName(Arrays.asList(parent));
                parentElement = new TreeItem<>(parentLayoutName);
                rootTree.getChildren().add(parentElement);
                layoutsName.put(parentLayoutName, parentElement);
                layoutElements.put(parentLayoutName, Arrays.asList(parent));

            }
        }
        return parentElement;
    }

}
