package com.console.domain;

public class AppAction<T extends ActionType, V> {

    public final T type;
    public final V value;

    public AppAction(T type) {
        this(type, null);
    }

    public AppAction(T type, V value) {
        this.type = type;
        this.value = value;
    }

    public String toString() {
        if (this.value != null) {
            return this.type.toString() + ": " + this.value.toString();
        } else {
            return this.type.toString();
        }
    }
}
