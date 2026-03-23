package org.example;

import java.util.*;

import lombok.extern.slf4j.Slf4j;

/**
 * 外部类：表示一个大型资源对象（模拟 WebView 或类似的持有大量资源的对象）
 */
class HeavyOuterObject {
    @SuppressWarnings("unused")
    private byte[] largeData = new byte[1024 * 1024]; // 1MB 虚拟大数据

    /**
     * 非静态内部类：隐式持有外部类引用
     * 这个内部类被注册到全局集合后，会导致整个外部类实例被永久持有
     */
    class InnerListener {
        public void onEvent() {
            log.info("Inner listener event triggered, accessing outer: {}", HeavyOuterObject.this);
        }
    }

    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HeavyOuterObject.class);
}

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：非静态内部类实例被注册到全局集合后，形成了对外部类的隐式强引用，导致整个外部类（及其所有成员）被永久持有
 * 测试意图：覆盖持有者对象类型为非静态内部类的场景，体现内部类引用导致外部类无法回收的问题
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    private static final List<Object> globalListeners = new ArrayList<>(); // 检查点, 测试因子(holder_object_type=non_static_inner_class)

    /**
     * 主方法，演示非静态内部类导致外部类引用残留
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {

        // 创建外部类实例
        HeavyOuterObject outer = new HeavyOuterObject();

        // 创建非静态内部类实例，隐式持有 outer 的引用
        HeavyOuterObject.InnerListener innerListener = outer.new InnerListener();
        log.info("Created non-static inner class instance");

        // 将内部类实例注册到全局列表
        globalListeners.add(innerListener);
        log.info("Registered inner listener to global list");

        // 业务完成后，即使 outer 本身不再使用，innerListener 仍在 globalListeners 中
        // 违规点：globalListeners 持有 innerListener 的引用，而 innerListener 隐式持有 outer 的引用
        // 结果：outer 及其 largeData 被永久持有，无法被垃圾回收
        // 正确做法：使用静态内部类或在清理时明确 remove innerListener
    }
}