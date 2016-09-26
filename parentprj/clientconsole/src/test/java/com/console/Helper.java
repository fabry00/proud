package com.console;

import com.console.domain.AppLayer;
import com.console.domain.AppNode;
import com.console.domain.IAppElement;

/**
 *
 * @author exfaff
 */
public class Helper {

    public IAppElement getLayer(String name) {
        IAppElement instance = new AppLayer.Builder(name).build();
        IAppElement node = getNode("Node");
        IAppElement layer2 = getNode("Layer2");
        instance.getNodes().addAll(node, layer2);
        return instance;
    }

    public IAppElement getNode(String name) {
        return new AppNode.Builder(name).build();
    }
}
