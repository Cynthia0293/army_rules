package org.example;

import java.util.*;
import java.lang.ref.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：虚引用（PhantomReference）通常用于追踪对象何时被回收，但误用虚引用作为直接引用保持，导致对象无法被垃圾回收
 * 测试意图：覆盖引用类型为phantom_misuse的场景，体现虚引用误用导致的回收阻止
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    private static final List<PhantomReference<byte[]>> phantomRefs = new ArrayList<>(); // 检查点, 测试因子(reference_type=phantom_misuse)
    private static final ReferenceQueue<byte[]> phantomQueue = new ReferenceQueue<>();

    /**
     * 演示虚引用误用导致的无法回收
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {

        // 误用虚引用作为对象引用保持者
        byte[] largeData = new byte[1024 * 1024]; // 1MB
        PhantomReference<byte[]> phantomRef = new PhantomReference<>(largeData, phantomQueue);
        phantomRefs.add(phantomRef);
        log.info("Created phantom reference to large data");

        // 清除本地引用
        largeData = null;
        log.info("Cleared local reference to large data");

        // 触发垃圾回收
        System.gc();

        // 违规点：虽然largeData被置为null，但phantomRefs列表仍然持有PhantomReference对象
        // PhantomReference本身并不阻止垃圾回收，但如果引用对象本身被强引用保持在某处，仍然无法回收
        // 正确做法：虚引用应该只用于追踪，不应该用作引用保持的手段

        log.info("Phantom reference misused, object reference tracking not properly implemented");
    }
}