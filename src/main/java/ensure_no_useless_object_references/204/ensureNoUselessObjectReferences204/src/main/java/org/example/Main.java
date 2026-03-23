package org.example;

import java.util.*;
import java.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：在线程池环境中，线程被复用来执行多个任务。如果某个任务在 ThreadLocal 中存储了业务数据但未清理，当线程被复用执行新任务时，旧数据仍然被该线程持有
 * 测试意图：覆盖持有者成员的存储作用域为线程池复用的场景，体现线程复用导致的引用残留
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    private static final ThreadLocal<Object> threadContext = new ThreadLocal<>(); // 检查点, 测试因子(storage_scope=thread_pool)
    private static final ExecutorService executor = Executors.newFixedThreadPool(2);

    /**
     * 演示线程池环境下的引用残留
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {

        // 第一个任务：设置 ThreadLocal 数据
        executor.submit(() -> {
            Object task1Data = new Object();
            threadContext.set(task1Data);
            log.info("Task 1: Set ThreadLocal data");

            // 模拟任务处理...
            Object retrieved = threadContext.get();
            log.info("Task 1: Retrieved data: {}", retrieved != null);

            // 违规点：任务完成后未调用 threadContext.remove()
            // 这会导致 task1Data 在线程被复用执行下一个任务时仍然被持有
        });

        // 通过睡眠确保第二个任务使用相同的线程
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 第二个任务：可能会看到第一个任务的数据
        executor.submit(() -> {
            Object task2Context = threadContext.get();
            log.info("Task 2: Found existing context from previous task: {}", task2Context != null);

            // 这说明线程池中的线程仍然持有来自前一个任务的数据
        });

        // 等待任务完成
        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        log.info("Thread pool task demonstration completed");
    }
}