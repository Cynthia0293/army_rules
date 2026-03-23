package org.example;

import java.util.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 异步任务完成回调管理器
 */
class AsyncTaskManager {
    private static final List<Runnable> callbacks = new ArrayList<>();

    public static void registerCallback(Runnable callback) {
        callbacks.add(callback);
        org.slf4j.LoggerFactory.getLogger(AsyncTaskManager.class).info("Callback registered");
    }

    public static void unregisterCallback(Runnable callback) {
        callbacks.remove(callback);
        org.slf4j.LoggerFactory.getLogger(AsyncTaskManager.class).info("Callback unregistered");
    }

    public static void executeCallbacks() {
        for (Runnable callback : callbacks) {
            callback.run();
        }
    }
}

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：异步回调注册到管理器后，虽然业务逻辑已完成，但回调对象从未从管理器中注销，导致回调被永久持有
 * 测试意图：覆盖编码缺陷类型为callback_not_unregister缺陷，体现回调注册后未注销的引用残留
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    /**
     * 演示回调未注销的引用残留
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {

        // 创建一个一次性回调
        Runnable tempCallback = new Runnable() { // 检查点, 测试因子(coding_defect=callback_not_unregister)
            @Override
            public void run() {
                log.info("Temporary callback executed");
            }
        };

        // 注册回调
        AsyncTaskManager.registerCallback(tempCallback);
        log.info("Temporary callback registered");

        // 执行回调
        AsyncTaskManager.executeCallbacks();

        // 违规点：回调从未注销
        // 正确做法：AsyncTaskManager.unregisterCallback(tempCallback);
        // 因为未执行此步骤，tempCallback被永久持有在管理器内
        log.info("Callback never unregistered, memory retained");
    }
}