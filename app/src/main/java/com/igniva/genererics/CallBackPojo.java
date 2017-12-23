package com.igniva.genererics;

/**
 * Created by ignivaandroid23 on 5/9/17.
 */

public class CallBackPojo<T> {

    public CallBackPojo(T value) {
        this.value = value;
    }

    public CallBackPojo() {
    }

    protected T value;

    public void setValue (T value) {
        this.value = value;
    }

    public T getValue () {
        return value;
    }
}