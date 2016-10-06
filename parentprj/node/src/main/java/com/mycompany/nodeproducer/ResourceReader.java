package com.mycompany.nodeproducer;

import com.proud.commons.DateUtils;
import org.apache.log4j.Logger;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.util.Date;
import java.util.Properties;

/**
 *
 * @author Fabrizio Faustinoni
 */
class ResourceReader implements Runnable {

    private static final String NODE_NAME_CONF = "node.name";
    private final Logger logger = Logger.getLogger(ResourceReader.class);
    private final MsgProducer sender;
    private final String nodeName;
    private final Sigar sigar = new Sigar();
    private final DateUtils dateUtils = new DateUtils();

    ResourceReader(Properties nodeProperties, MsgProducer sender) {
        this.sender = sender;
        nodeName = nodeProperties.getProperty(NODE_NAME_CONF);      
    }

    @Override
    public void run() {
        Double cpu = getCpuUsage();
        Long ram = getUsedMemory();

        logger.debug(nodeName + " CPU VALUE: " + cpu + " factor: " + " ram: " + ram);

        Date xValue = dateUtils.getNowDate();

        Message message = new Message();
        message.msg = "[" + dateUtils.formatDate(xValue) + "] " + nodeName
                + " CPU VALUE: " + cpu + " factor: " + " ram: " + ram;

        sender.send(message);
    }

    private Double getCpuUsage() {
        try {
            CpuPerc pCpu = sigar.getCpuPerc();
            return pCpu.getCombined() * 100;
        } catch (SigarException ex) {
            logger.error(ex);
        }

        return null;
    }

    private Long getUsedMemory() {
        try {
            Mem mem = sigar.getMem();
            return mem.getActualUsed() / 1024 / 1024;
        } catch (SigarException ex) {
            logger.error(ex);
        }
        return null;
    }

    /*private void getNodeStatus() {
        if (!failureDetected) {
            Random random = new Random();
            int rand = random.nextInt(100);
            if (rand > ANOMALY_LIMIT && rand < FAILURE_LIMIT) {
                builder.isInAbnormalState();
            } else if (rand > FAILURE_LIMIT) {
                failureDetected = true;
                builder.isFailureDetected();
            }
        } else {
            builder.isFailureDetected();
        }
    }*/
}
