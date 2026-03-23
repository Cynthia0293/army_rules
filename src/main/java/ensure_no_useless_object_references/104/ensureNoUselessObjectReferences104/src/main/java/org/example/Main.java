package org.example;

import lombok.extern.slf4j.Slf4j;

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：使用 ThreadLocal 存储线程本地数据后，未在业务结束时调用 remove() 清理，导致 ThreadLocal 中的对象被永久持有（特别是在线程池环境中线程被复用时）
 * 测试意图：覆盖持有者对象类型为 java.lang.ThreadLocal 的场景，体现 ThreadLocal 未正确清理导致的引用残留
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    private static final ThreadLocal<Object> CONTEXT_HOLDER = new ThreadLocal<>(); // 检查点, 测试因子(holder_object_type=thread_local)

    /**
     * 主方法，模拟在 ThreadLocal 中存储请求上下文而未清理的场景
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {

        Object requestContext = new Object(); // 设置线程本地变量
        CONTEXT_HOLDER.set(requestContext);
        log.info("Request context stored in ThreadLocal.");

        Object retrievedContext = CONTEXT_HOLDER.get();
        log.info("Context retrieved: {}", retrievedContext != null);

    }
}