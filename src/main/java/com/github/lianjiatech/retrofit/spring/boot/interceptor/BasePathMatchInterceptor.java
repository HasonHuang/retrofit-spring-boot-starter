package com.github.lianjiatech.retrofit.spring.boot.interceptor;

import java.io.IOException;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import lombok.Setter;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 路径匹配拦截器, 如果使用spring-bean方式，使用原型模式
 *
 * @author 陈添明
 */
@Setter
public abstract class BasePathMatchInterceptor implements Interceptor {

    protected String[] include;

    protected String[] exclude;

    protected PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        String path = request.url().encodedPath();

        if (isMatch(exclude, path)) {
            return chain.proceed(request);
        }

        if (!isMatch(include, path)) {
            return chain.proceed(request);
        }
        return doIntercept(chain);
    }

    /**
     * do intercept
     *
     * @param chain interceptor chain
     * @return http Response
     * @throws IOException IOException
     */
    protected abstract Response doIntercept(Chain chain) throws IOException;

    /**
     * 当前http的url路径是否与指定的patterns匹配。<br></>
     * Whether the current http URL path matches the specified patterns
     *
     * @param patterns the specified patterns
     * @param path     http URL path
     * @return 匹配结果
     */
    private boolean isMatch(String[] patterns, String path) {
        if (patterns == null || patterns.length == 0) {
            return false;
        }
        for (String pattern : patterns) {
            boolean match = pathMatcher.match(pattern, path);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
