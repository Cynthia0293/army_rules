package org.example;

import java.util.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 全局对象复活容器
 */
class ResurrectionContainer {
    private static final List<Object> resurrectedObjects = new ArrayList<>();

    public static void storeResurrected(Object obj) {
        resurrectedObjects.add(obj);
        org.slf4j.LoggerFactory.getLogger(ResurrectionContainer.class).info("Object resurrected");
    }
}

/**
 * 可复活的对象
 */
class ResurrectableObject {
    private byte[] largeData = new byte[1024 * 1024]; // 1MB

    @Override
    protected void finalize() throws Throwable {
        // 危险：在finalize中将this注册到全局容器，导致对象复活
        ResurrectionContainer.storeResurrected(this); // 检查点, 测试因子(coding_defect=finalize_resurrection)
        org.slf4j.LoggerFactory.getLogger(ResurrectableObject.class).info("Object resurrected in finalize");
        super.finalize();
    }
}

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：在finalize方法中将this注册到全局容器，导致原本应该被垃圾回收的对象被复活并永久持有
 * 测试意图：覆盖编码缺陷类型为finalize_resurrection缺陷，体现finalize误用导致的对象复活和内存泄漏
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    /**
     * 演示finalize方法导致的对象复活
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {

        // 创建对象
        ResurrectableObject obj = new ResurrectableObject();
        log.info("Object created");

        // 清除本地引用，对象应该被垃圾回收
        obj = null;
        log.info("Local reference cleared");

        // 触发垃圾回收
        System.gc();
        log.info("Garbage collection triggered");

        // 等待finalize执行
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 违规点：虽然我们清除了本地引用，但finalize方法将对象复活
        // 对象及其1MB的largeData被ResurrectionContainer永久持有
        // 这违反了垃圾回收的规则，导致内存泄漏

        // 正确做法：不要在finalize中进行复活操作，finalize应该只用于清理资源
        // 最好避免使用finalize，改用try-with-resources或其他显式资源管理方式
        log.info("Object was resurrected due to finalize misuse");
    }
}