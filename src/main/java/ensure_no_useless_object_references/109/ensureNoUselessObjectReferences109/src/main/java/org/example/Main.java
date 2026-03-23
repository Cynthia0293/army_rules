package org.example;

import java.util.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 大型资源对象类
 */
class ResourceContext {
    @SuppressWarnings("unused")
    private byte[] data = new byte[1024 * 1024]; // 1MB
}

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：匿名内部类实例被保存到全局集合后，隐式持有外部类实例的引用，导致外部类及其资源被永久持有
 * 测试意图：覆盖持有者对象类型为匿名内部类的场景，体现匿名类隐式持有外部类导致无法回收的问题
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    private static final List<Runnable> handlers = new ArrayList<>(); // 检查点, 测试因子(holder_object_type=anonymous_class)

    /**
     * 模拟匿名内部类导致外部类引用残留
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {

        // 创建外部类实例
        ResourceContext context = new ResourceContext();

        // 创建匿名内部类实例，隐式持有 context 的引用
        Runnable anonymousTask = new Runnable() {
            @Override
            public void run() {
                log.info("Executing task with context: {}", context);
            }
        };

        // 注册匿名类实例到全局列表
        handlers.add(anonymousTask);
        log.info("Anonymous runnable registered to global list");

        // 业务完成后，即使 context 本身不再使用，anonymousTask 仍在 handlers 中
        // 违规点：handlers 持有 anonymousTask 的引用，而 anonymousTask 隐式持有 context 的引用
        // 结果：context 及其 data 被永久持有，无法被垃圾回收
    }
}