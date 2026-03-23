package org.example;

import java.util.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：在热部署或动态加载场景中，通过自定义类加载器加载的类的实例被全局容器持有，导致类加载器及其加载的类库无法被垃圾回收
 * 测试意图：覆盖持有者对象类型为类加载器引用的场景，体现类加载器及其关联类无法释放的问题
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    private static final List<Class<?>> loadedClasses = new ArrayList<>(); // 检查点, 测试因子(holder_object_type=classloader_leak)
    private static ClassLoader customLoader; // 自定义类加载器

    /**
     * 演示类加载器引用残留导致的内存问题
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {

        // 模拟自定义类加载器（在实际热部署场景中使用）
        customLoader = Thread.currentThread().getContextClassLoader();
        log.info("Custom class loader initialized");

        try {
            // 通过自定义类加载器加载 Main 类自身作为演示
            Class<?> dynamicallyLoadedClass = customLoader.loadClass("org.example.Main");
            loadedClasses.add(dynamicallyLoadedClass);
            log.info("Loaded class: {}", dynamicallyLoadedClass.getName());
        } catch (ClassNotFoundException e) {
            log.error("Failed to load class", e);
        }

        // 违规点：loadedClasses 持有对类对象的引用，而这些类对象持有对类加载器的隐式引用
        // 即使应用卸载了旧版本代码并加载了新版本，旧版本类仍然通过 loadedClasses 被持有
        // 这导致类加载器及其加载的所有类都无法被垃圾回收
        // 正确做法：应该在热部署时清理 loadedClasses 并显式置 null
    }
}