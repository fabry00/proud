package com.console.domain;

/**
 * Metric class
 * @author Fabrizio Faustinoni
 */
public enum AppMetric {
    CPU("Cpu Monitoring (in %)"),
    MEMORY("Memory Monitoring (in MB)"),
    SPACE("Space Monitoring (in KB)"),
    NETWORK("Network Monitoring (in Kb/s)");

    private final String desc;

    AppMetric(String title) {
        this.desc = title;
    }

    public String getDesc() {
        return this.desc;
    }

    @Override
    public String toString() {
        return this.name();
    }

}
