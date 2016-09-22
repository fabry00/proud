package com.console.view.systemlayout;

import com.console.view.systemlayout.element.ISystemElement;
import org.apache.log4j.Logger;

/**
 *
 * @author fabry
 */
public class SystemLayoutManager implements ISystemLayoutManager {

    private final Logger logger = Logger.getLogger(SystemLayoutManager.class);

    @Override
    public void changeLayout(ISystemElement elementToShow) {
        logger.debug("Change layout " + elementToShow.getType() + " " + elementToShow.getName());
    }

}
