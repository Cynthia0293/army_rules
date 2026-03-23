package org.example;

import java.util.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 单例管理器
 */
class SingletonManager {
    private static SingletonManager instance = new SingletonManager();
    private Map<String, Object> contextData = new HashMap<>(); // 检查点, 测试因子(storage_scope=singleton)

    private SingletonManager() {}

    public static SingletonManager getInstance() {
        return instance;
    }

    public void store(String key, Object value) {
        contextData.put(key, value);
        org.slf4j.LoggerFactory.getLogger(SingletonManager.class).info("Stored data in singleton");
    }

    public Object retrieve(String key) {
        return contextData.get(key);
    }
}

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：单例对象的成员变量持有业务数据，由于单例不会被垃圾回收，整个数据也不会被释放
 * 测试意图：覆盖持有者成员的存储作用域为单例对象的场景，体现单例生存期間的业务数据无法释放
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    /**
     * 演示单例内数据永久持有
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {

        // 程序的第一个请求
        SingletonManager manager = SingletonManager.getInstance();
        Object temporaryData = new Object();
        manager.store("request_1", temporaryData);
        log.info("Stored temporary data in singleton");

        // 第一个请求完成后...
        // 即使不再使用 temporaryData，它已经被单例持有

        // 程序的第二个请求
        Object anotherData = new Object();
        manager.store("request_2", anotherData);
        log.info("Stored another temporary data");

        // 违规点：单例管理器会一直持有第一个请求的数据
        // 尽管程序已经处理了新请求，旧数据仍然避់
        // 正确做法是对单例进行业务数据的接处理，例如使lru及时清空旧数据
    }
}