package com.proud.server.resource;

import com.proud.commons.AppHelper;
import com.proud.commons.DateUtils;
import com.proud.domain.PredictionType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * System Status controller
 * Created by Fabrizio Faustinoni on 03/10/2016.
 */
@RestController
public class SystemStatusController {

    @RequestMapping("/systemstatus")
    public SystemStatus systemstatus() {
        return new SystemStatus(PredictionType.DETECTED,PredictionType.NOT_DETECTED);
    }
}
