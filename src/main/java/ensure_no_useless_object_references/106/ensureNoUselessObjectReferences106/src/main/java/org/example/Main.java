package org.example;

import java.util.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：监听器注册到静态事件发射器后，在业务生命周期结束时未注销，导致监听器对象被长期持有
 * 测试意图：覆盖持有者对象类型为自定义监听器的场景
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    private static final EventEmitter emitter = new EventEmitter();

    /**
     * 主方法，模拟业务处理
     *
     * @param args 命令行参数
     */

    public static void main(String[] args) {
        EventListener temporaryListener = event -> log.info("收到事件: {}", event); // 创建临时监听器
        emitter.addListener(temporaryListener);
        emitter.emit("business_data");
        log.info("业务结束，监听器残留");
    }
}

/**
 * 辅助类：事件发射器
 */
@Slf4j
class EventEmitter {
    private static final List<EventListener> listeners = new ArrayList<>(); // 检查点, 测试因子(holder_object_type=custom_listener)

    public void addListener(EventListener listener) {
        listeners.add(listener);
    }

    public void emit(String event) {
        for (EventListener listener : listeners) {
            listener.onEvent(event);
        }
    }
}

/**
 * 辅助接口：监听器
 */
interface EventListener {
    void onEvent(String event);
}