package com.medical.utils;

public class BaseContext
{

    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static String getCurrentId() {
        return threadLocal.get().toString();
    }

    public static void removeCurrentId() {
        threadLocal.remove();
    }

}
