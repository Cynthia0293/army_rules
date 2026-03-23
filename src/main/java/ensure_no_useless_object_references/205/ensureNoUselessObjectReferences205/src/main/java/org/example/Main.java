package org.example;

import java.util.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Web请求上下文
 */
class WebRequestContext {
    private static final ThreadLocal<Map<String, Object>> requestScope = new ThreadLocal<>();

    public static void setRequestAttribute(String key, Object value) {
        Map<String, Object> attrs = requestScope.get();
        if (attrs == null) {
            attrs = new HashMap<>();
            requestScope.set(attrs);
        }
        attrs.put(key, value);
    }

    public static Object getRequestAttribute(String key) {
        Map<String, Object> attrs = requestScope.get();
        return attrs != null ? attrs.get(key) : null;
    }

    public static void clearRequestContext() {
        requestScope.remove();
    }
}

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：Web请求处理结束后，请求作用域需要被清空。如果对请求作用域中的ThreadLocal未及时清理，会导致请求数据永久被持有
 * 测试意图：覆盖持有者成员的存储作用域为Web请求作用域的场景，体现请求作用域数据未被清理的污染
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    /**
     * 演示Web请求作用域的数据永久持有
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {

        // 模拟Web请求 1：用户登录
        WebRequestContext.setRequestAttribute("userId", "user123"); // 检查点, 测试因子(storage_scope=web_request)
        WebRequestContext.setRequestAttribute("sessionId", UUID.randomUUID().toString());
        Object userIdData = WebRequestContext.getRequestAttribute("userId");
        log.info("Request 1: Processed user login, userId={}", userIdData);

        // 请求 1 处理完成，但未清理请求作用域
        // 违规点：用户的userId和sessionId仍然被 ThreadLocal 持有
        // 在多线程环境中，下一个使用该线程的请求可能会看到上一个请求的数据

        // 模拟Web请求 2：另一个用户查询
        // 在线程池场景中，可能会复用之前的线程
        Object previousUserData = WebRequestContext.getRequestAttribute("userId");
        log.info("Request 2: Previous request data still accessible: {}", previousUserData);

        // 正确做法：请求处理完毕后显式清理作用域
        // WebRequestContext.clearRequestContext();
    }
}