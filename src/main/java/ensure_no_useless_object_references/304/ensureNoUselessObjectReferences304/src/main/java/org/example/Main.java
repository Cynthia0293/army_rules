package org.example;

import java.util.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：数组元素被删除后，虽然数组的有效大小减少了，但被删除位置的引用未被显式赋为null，导致该位置的对象无法被垃圾回收
 * 测试意图：覆盖编码缺陷类型为no_null_assign缺陷，体现数组下标打回但元素未置空的问题
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    private static final Object[] elements = new Object[100]; // 检查点, 测试因子(coding_defect=no_null_assign)
    private static int size = 0;

    /**
     * 演示数组元素未赋null的引用残留
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {

        // 添加元素
        for (int i = 0; i < 10; i++) {
            elements[size++] = new Object();
        }
        log.info("Added 10 objects, size is now {}", size);

        // 删除元素：直接减少size，但不赋null
        // 违规点：虽然size减少了，但elements[9]仍然有旧的对象引用
        size--;
        log.info("Decreased size to {}, but elements[{}] still has reference", size, size);

        // 正确做法：elements[size] = null; size--;
        // 因为未执行此步骤，elements[9]及其引用的对象被永久持有
    }
}