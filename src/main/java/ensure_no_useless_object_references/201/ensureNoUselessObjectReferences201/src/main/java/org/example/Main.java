package org.example;

import java.util.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：将业务数据缓存在静态成员变量，缓存永久存在于程序的手封或者应用程序嬢閻，导致数据无法被垃圾回收
 * 测试意图：覆盖持有者成员的存储作用域为静态成员变量的场景，体现静态引用导致的业务数据永久持有
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    private static final Map<String, Object> DATA_CACHE = new HashMap<>(); // 检查点, 测试因子(storage_scope=static)

    /**
     * 演示静态成员引用残留
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {

        Object temporaryBusinessData = new Object();
        DATA_CACHE.put("session_12345", temporaryBusinessData);
        log.info("Cached business data in static HashMap.");

        // ется 业务处理...

        // 违规点：CACHE 作为静态成员会一直持有数据
        // 即使业务完成后也不会被清空，永远废綠内存
        // 正确做法：应该在需要的时候明确释放会话上下文数据
    }
}