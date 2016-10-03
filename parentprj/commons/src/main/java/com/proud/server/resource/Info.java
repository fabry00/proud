package com.proud.server.resource;

/**
 * Info class
 * Created by Fabrizio Faustinon on 02/10/2016.
 */
public class Info {

    private final long id;
    private final String date;
    private final String jre;
    private final String jvm;
    private final String appVersion;

    public Info(long id, String date, String appVersion, String jvm, String jre) {
        this.id = id;
        this.date = date;
        this.appVersion = appVersion;
        this.jre = jre;
        this.jvm = jvm;
    }

    public long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public String getJre() {
        return jre;
    }

    public String getJvm() {
        return jvm;
    }
}