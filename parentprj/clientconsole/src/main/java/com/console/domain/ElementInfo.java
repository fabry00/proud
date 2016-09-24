package com.console.domain;

/**
 *
 * @author exfaff
 */
public class ElementInfo {

    public enum Type {

        IP
    };

    public ElementInfo(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    private final String value;
    private final Type type;

    public String getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }
}
