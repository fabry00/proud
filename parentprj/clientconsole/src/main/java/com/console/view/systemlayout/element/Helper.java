package com.console.view.systemlayout.element;

import com.console.view.systemlayout.ISystemLayoutManager;
import java.util.Arrays;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author fabry
 */
class Helper {

    void changeLayour(MouseEvent event, ISystemLayoutManager layoutManager, ISystemElement elementClicked) {
        if (!event.getButton().equals(MouseButton.PRIMARY) || event.getClickCount() < 2) {
            return;
        }
        layoutManager.changeLayout(Arrays.asList(elementClicked));
    }

}
