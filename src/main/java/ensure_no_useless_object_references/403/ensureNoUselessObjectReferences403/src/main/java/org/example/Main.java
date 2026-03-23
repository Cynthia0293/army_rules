package org.example;

import java.util.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：使用软引用为缓存结构，但未设置淘汰策略。虽然JVM会在内存不足时回收软引用，但没有淘汰策略的情况下可能导致频繁的软引用回收
 * 测试意图：覆盖引用类型为soft_cache_no_evict的场景，体现缺少淘汰策略导致的缓存管理问题
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    private static final List<java.lang.ref.SoftReference<byte[]>> softCache = new ArrayList<>(); // 检查点, 测试因子(reference_type=soft_cache_no_evict)

    /**
     * 演示软引用缓存无淘汰策略的问题
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {

        // 添加大量软引用对象到缓存
        for (int i = 0; i < 100; i++) {
            byte[] largeData = new byte[1024 * 100]; // 100KB each
            java.lang.ref.SoftReference<byte[]> softRef = new java.lang.ref.SoftReference<>(largeData);
            softCache.add(softRef);
        }
        log.info("Added 100 soft references to cache");

        // 违规点：缓存没有淘汰策略，所有软引用都被保存在列表中
        // 即使被垃圾回收，回收的是缓冲区内容，但SoftReference对象本身仍在列表中
        // 正确做法：实现LRU、LFU等淘汰策略来管理缓存大小
        log.info("Soft cache without eviction strategy");
    }
}