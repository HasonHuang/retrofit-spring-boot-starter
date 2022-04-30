package com.github.lianjiatech.retrofit.spring.boot.interceptor;

import okhttp3.Interceptor;

/**
 * @author 陈添明
 */
public abstract class BaseLoggingInterceptor implements Interceptor {

    /**
     * 日志打印级别
     * Log printing level
     */
    protected final LogLevel logLevel;

    /**
     * 日志打印策略
     * Log printing strategy
     */
    protected final LogStrategy logStrategy;


    public BaseLoggingInterceptor(LogLevel logLevel, LogStrategy logStrategy) {
        this.logLevel = logLevel;
        this.logStrategy = logStrategy;
    }
}
