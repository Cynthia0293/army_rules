package org.example;

import java.util.*;
import java.lang.ref.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：使用弱引用和ReferenceQueue来追踪对象回收，但未定期处理ReferenceQueue中的回收引用，导致引用对象在队列中积累
 * 测试意图：覆盖引用类型为queue_not_cleaned的场景，体现ReferenceQueue未处理导致的引用残留
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    private static final ReferenceQueue<byte[]> refQueue = new ReferenceQueue<>(); // 检查点, 测试因子(reference_type=queue_not_cleaned)
    private static final List<WeakReference<byte[]>> weakRefs = new ArrayList<>();

    /**
     * 演示ReferenceQueue未处理导致的引用残留
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {

        // 创建弱引用并关联ReferenceQueue
        for (int i = 0; i < 10; i++) {
            byte[] data = new byte[1024 * 100]; // 100KB
            WeakReference<byte[]> weakRef = new WeakReference<>(data, refQueue);
            weakRefs.add(weakRef);
        }
        log.info("Created 10 weak references with ReferenceQueue");

        // 触发垃圾回收
        System.gc();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 违规点：ReferenceQueue中可能有已回收的引用，但我们从未处理过
        // 这些引用对象会在队列中积累，占用内存
        // 正确做法：定期调用 refQueue.poll() 或 refQueue.remove() 来清理已回收的引用

        log.info("ReferenceQueue never processed, collected references accumulate");
    }
}