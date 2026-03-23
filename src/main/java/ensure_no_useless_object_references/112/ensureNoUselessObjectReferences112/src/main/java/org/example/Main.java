package org.example;

import java.util.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：使用 WeakHashMap 作为缓存，但 WeakHashMap 本身作为静态成员被强引用持有，导致虽然 WeakHashMap 的键是弱引用，但整个容器无法被回收
 * 测试意图：覆盖持有者对象类型为 WeakHashMap 的场景，体现容器本身强引用持有导致的问题
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    private static final WeakHashMap<String, byte[]> weakCache = new WeakHashMap<>(); // 检查点, 测试因子(holder_object_type=weak_hash_map)

    /**
     * 演示 WeakHashMap 容器引用残留
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {

        // 添加数据到 WeakHashMap
        String key = "cache_key";
        weakCache.put(key, new byte[1024 * 100]); // 100KB
        log.info("Added 100KB data to WeakHashMap");

        // 模拟业务处理
        log.info("WeakHashMap current size: {}", weakCache.size());

        // 业务完成后，虽然 WeakHashMap 中的值可能被回收（如果 key 变为弱引用），
        // 但 weakCache 本身作为静态成员仍然被程序持有
        // 违规点：weakCache 容器作为静态引用永久存在
        // 正确做法：应该根据业务需要，在不再使用时将 weakCache 置 null
    }
}