package org.example;

import java.util.*;

import lombok.extern.slf4j.Slf4j;

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：使用静态 HashMap 存储数据后未进行清理（如调用 remove 或 clear），导致对象被长期非法持有
 * 测试意图：覆盖持有者对象类型为 java.util.HashMap 的场景
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-22
 */
@Slf4j
public class Main {


    private static final Map<String, Object> DATA_CACHE = new HashMap<>(); // 检查点, 测试因子(holder_object_type=hash_map)

    /**
     * 主方法，执行业务逻辑并模拟引用残留
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {

        Object temporaryBusinessData = new Object(); // 模拟业务产生的一个临时对象
        DATA_CACHE.put("session_12345", temporaryBusinessData);
        log.info("Business process finished, but data remains in HashMap.");

    }
}