package org.example;

import java.util.*;

import lombok.extern.slf4j.Slf4j;

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：使用 Object[] 数组存储对象，当数组大小减少后，未将释放的位置明确赋值为 null，导致该位置的对象仍被数组引用
 * 测试意图：覆盖持有者对象类型为 Object[] 的场景，体现数组元素删除后未赋 null 导致的引用残留
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    private static Object[] objects = new Object[10]; // 检查点, 测试因子(holder_object_type=object_array)
    private static int size = 0;

    /**
     * 主方法，模拟动态数组管理中未赋 null 导致引用残留的场景
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {

        objects[0] = new Object();
        objects[1] = new Object();
        size = 2;
        log.info("Added 2 objects to Object[] array.");

        size--;
        log.info("Decreased size to {}, but objects[0] is never set to null.", size);

    }
}