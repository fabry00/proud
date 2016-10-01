package com.console.domain;

/**
 *
 * @author Fabrizio Faustinoni
 */
public enum AppMetric {
    CPU("Cpu Monitoring (in %)"),
    MEMORY("Memory Monitoring (in MB)"),
    SPACE("Space Monitoring (in KB)"),
    NETWORK("Network Monitoring (in Kb/s)");

    private final String title;

    AppMetric(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

}
