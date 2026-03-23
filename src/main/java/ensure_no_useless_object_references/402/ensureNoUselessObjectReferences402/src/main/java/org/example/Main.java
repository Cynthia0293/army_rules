package org.example;

import java.util.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：WeakHashMap 作为可自动清理的缓存结构，但 key 被强引用保持，导致弱引用失效，WeakHashMap 无法做自动清理
 * 测试意图：覆盖引用类型为weak_map_strong_hold的场景，体现强引用持有 key 导致的清理失效
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    private static final WeakHashMap<String, byte[]> weakCache = new WeakHashMap<>(); // 检查点, 测试因子(reference_type=weak_map_strong_hold)
    private static String strongKey; // 强引用持有的 key

    /**
     * 演示强引用阻止弱引用清理
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 添加缓存数据到 WeakHashMap
        strongKey = "cache_key_" + System.currentTimeMillis(); // 强引用保持 key
        byte[] cachedData = new byte[1024 * 100]; // 100KB
        weakCache.put(strongKey, cachedData);
        log.info("Added data to WeakHashMap with strong key reference");

        // WeakHashMap 本应能在 key 无强引用时自动清理该条目
        // 但由于 key 被 strongKey 强引用持有，WeakHashMap 无法清理该条目
        // 违规点：key 被 strongKey 保持强引用，故 WeakHashMap 无法删除该条目
        // 正确做法：不要用强引用保持 key，或使用其他清理策略（如显式清理或 LRU）

        log.info("Cache entry cannot be automatically cleaned due to strong key reference");
    }
}