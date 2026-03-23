package org.example;

import java.util.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：在热部署或动态加载场景中，旧版本的类被新的类加载器加载。如果应用保留了对旧版本类实例的引用，会导致旧版本的类加载器及其加载的所有类都无法被回收，造成 PermGen/Metaspace 内存泄漏
 * 测试意图：覆盖持有者成员的存储作用域为类加载器重部署的场景，体现热部署导致的类加载器及关联类的永久持有
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    private static final List<Object> versionedObjects = new ArrayList<>(); // 检查点, 测试因子(storage_scope=classloader_redeploy)

    /**
     * 演示类加载器重部署导致的引用残留
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {

        // 模拟应用版本 V1 加载的类实例
        try {
            // 加载当前类作为演示（实际热部署场景会通过自定义 ClassLoader 加载新版本类）
            Class<?> mainClass = Class.forName("org.example.Main");
            log.info("Loaded class version: {}", mainClass.getName());

            // 模拟新版本声称部署后，旧实例仍被保留
            Object v1Instance = mainClass.getDeclaredConstructor().newInstance();
            versionedObjects.add(v1Instance);
            log.info("Stored instance from class loader version V1");
        } catch (Exception e) {
            log.error("Failed to simulate version loading", e);
        }

        // 模拟应用版本 V2 进行部署
        // 理想情况下，V1 的所有类实例应该被清理，从而 V1 的类加载器可以被回收
        log.info("Deploying application version V2...");

        // 违规点：versionedObjects 仍然持有 V1 版本的类实例
        // 导致 V1 的 ClassLoader 及其加载的所有类都无法被垃圾回收
        // 这会导致 Metaspace（或 PermGen）的内存逐渐增长，最终 OOM

        // 正确做法：
        // 1. 在部署新版本前，清理所有旧版本实例引用：versionedObjects.clear()
        // 2. 或者使用弱引用持有这些对象
        // 3. 或者设计热部署机制确保单独的 ClassLoader 实例可以完全卸载\\

        log.info("V1 classes still referenced by versionedObjects, version");
    }
}