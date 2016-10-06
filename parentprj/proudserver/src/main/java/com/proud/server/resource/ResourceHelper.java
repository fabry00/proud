package com.proud.server.resource;

import com.proud.commons.ConfigUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * Resource helper
 * Created by Fabrizio Faustinoni on 03/10/2016.
 */
@Component
@Service
public class ResourceHelper {

    private static Properties appConfigs;

    public static void setAppProperties(Properties appConfigs) {
        ResourceHelper.appConfigs = appConfigs;
    }

    public String getAppVersion() {
        return appConfigs.getProperty(ConfigUtils.KEY_VERSION);
    }
}
