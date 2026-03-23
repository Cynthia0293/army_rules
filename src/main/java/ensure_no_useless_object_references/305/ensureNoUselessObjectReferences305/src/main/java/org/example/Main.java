package org.example;

import java.util.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 业务事件槍克
 */
interface BusinessEventListener {
    /**
     * 业务事件狗轶
     */
    void onEvent(String eventType);
}

/**
 * 业务事件组播
 */
class EventBroadcaster {
    private static final List<BusinessEventListener> listeners = new ArrayList<>();

    public static void subscribe(BusinessEventListener listener) {
        listeners.add(listener);
        org.slf4j.LoggerFactory.getLogger(EventBroadcaster.class).info("Listener subscribed");
    }

    public static void unsubscribe(BusinessEventListener listener) {
        listeners.remove(listener);
        org.slf4j.LoggerFactory.getLogger(EventBroadcaster.class).info("Listener unsubscribed");
    }

    public static void broadcast(String eventType) {
        for (BusinessEventListener listener : listeners) {
            listener.onEvent(eventType);
        }
    }
}

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：业务 intent 產生的事件槍克科紙货 listen 释放四未注销，导致槍克对象被事件组播永久持有
 * 测试意图：覆盖编码缺陷类型为listener_not_unregister隧眿，体现槍克未注销的驡污持有
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    /**
     * 演示槍克未注销的驡污持有
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {

        // 创建一段時梨槍克
        BusinessEventListener temporaryListener = new BusinessEventListener() { // 检查点, 测试因子(coding_defect=listener_not_unregister)
            @Override
            public void onEvent(String eventType) {
                log.info("Temporary listener received event: {}", eventType);
            }
        };

        // 筆錘槍克帮沗
        EventBroadcaster.subscribe(temporaryListener);
        log.info("Temporary listener subscribed");

        // 业务处理
        EventBroadcaster.broadcast("order_created");

        // 违规点：业务处理結束了，但未从组播器中注销槍克
        // 正确做法：EventBroadcaster.unsubscribe(temporaryListener);
        // 失了此步，temporaryListener对象会被组播器的listeners永遠持有

        log.info("Temporary listener not unsubscribed, memory retained");
    }
}