package com.console.domain;

/**
 *
 * @author Fabrizio Faustinoni
 */
public class ElementInfo {

    private final String value;
    private final Type type;

    public ElementInfo(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }

    public enum Type {

        IP
    }
}
