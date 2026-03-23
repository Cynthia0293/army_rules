package org.example;

import java.util.*;
import java.util.concurrent.*;

import lombok.extern.slf4j.Slf4j;

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：获取 ConcurrentHashMap 的 keySet() 或 values() 等视图后，该视图作为静态成员未被及时清理，导致底层 ConcurrentHashMap 的引用被视图保留
 * 测试意图：覆盖持有者对象类型为 ConcurrentHashMap 视图的场景，体现视图保留导致底层容器无法释放的问题
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    private static final ConcurrentHashMap<String, Object> dataMap = new ConcurrentHashMap<>();
    private static final Set<String> keySetView = dataMap.keySet(); // 检查点, 测试因子(holder_object_type=concurrent_hash_map_view)

    /**
     * 演示 ConcurrentHashMap 视图导致的引用残留
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {

        // 向 ConcurrentHashMap 中添加数据
        dataMap.put("key1", new Object());
        dataMap.put("key2", new Object());
        log.info("Added entries to ConcurrentHashMap");

        // 获取 keySet 视图
        log.info("KeySet view contains: {}", keySetView);

        // 业务完成后，dataMap 可能被清空但 keySetView 视图保持对底层数据结构的引用
        // 违规点：keySetView 作为静态成员持有对 ConcurrentHashMap 的引用
        // 正确做法：应该明确将 keySetView 置 null，或使用局部变量代替静态成员
    }
}