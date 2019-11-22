package com.handsomemark.calendarlib;

import android.util.Log;

/**
 *
 * 日志封装
 */

public class ForLog {
    static String className;//类名
    static String methodName;//方法名
    static int lineNumber;//行数

    /**
     * 判断是否可以调试
     *
     * @return
     */
    public static boolean isDebuggable() {
        return BuildConfig.DEBUG;
    }

    private static String createLog(String log) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("================");
        buffer.append(methodName);
        buffer.append("(").append(className).append(":").append(lineNumber).append(")================:");
        buffer.append(log);
        return buffer.toString();
    }

    /**
     * 获取文件名、方法名、所在行数
     *
     * @param sElements
     */
    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    public static void e(Object... messages) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        String message="";
        for(Object m:messages){
            message+=m+"<--->";
        }
        Log.e(className, createLog(message));
    }


    public static void i(Object... messages) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        String message="";
        for(Object m:messages){
            message+=m;
        }
        Log.i(className, createLog(message));
    }

    public static void d(Object... messages) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        String message="";
        for(Object m:messages){
            message+=m;
        }
        Log.d(className, createLog(message));
    }

    public static void v(Object... messages) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        String message="";
        for(Object m:messages){
            message+=m;
        }
        Log.v(className, createLog(message));
    }


    public static void w(Object... messages) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        String message="";
        for(Object m:messages){
            message+=m;
        }
        Log.w(className, createLog(message));
    }
}