package org.example;

import java.util.*;
import lombok.extern.slf4j.Slf4j;


/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：异步回调注册到全局任务执行器后，业务逻辑完成时未注销该回调，导致回调对象被执行器的静态列表长期持有
 * 测试意图：覆盖持有者对象类型为自定义回调的场景，体现回调虽然一次性使用但因为未注销而被永久持有的问题
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    private static final AsyncTaskExecutor executor = new AsyncTaskExecutor();

    /**
     * 主方法，模拟异步任务完成场景
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {

        TaskCallback singleUseCallback = new TaskCallback() {
            @Override
            public void onTaskComplete(String result) {
                log.info("Task completed with result: {}", result);
            }
        };

        executor.registerCallback(singleUseCallback);
        executor.completeTask("success");

        // 违规点：回调已执行完毕，但从未从 executor 的 callbacks 列表中移除
        // 正确做法应该是 executor.unregisterCallback(singleUseCallback)
        // singleUseCallback 被永久持有，即使业务场景已完成
    }
}




/**
 * 异步任务处理器，用于演示回调注册但未注销导致的引用残留
 */
class AsyncTaskExecutor {
    private static final List<TaskCallback> callbacks = new ArrayList<>(); // 检查点, 测试因子(holder_object_type=custom_callback)

    /**
     * 注册异步任务完成回调
     *
     * @param callback 回调实例
     */
    public void registerCallback(TaskCallback callback) {
        callbacks.add(callback);
        log.info("Callback registered");
    }

    /**
     * 模拟异步任务完成，触发所有回调
     *
     * @param result 任务结果
     */
    public void completeTask(String result) {
        for (TaskCallback callback : callbacks) {
            callback.onTaskComplete(result);
        }
    }

    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AsyncTaskExecutor.class);
}

/**
 * 任务完成回调接口
 */
interface TaskCallback {
    /**
     * 任务完成时的回调
     *
     * @param result 结果数据
     */
    void onTaskComplete(String result);
}