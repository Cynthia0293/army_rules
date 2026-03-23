package org.example;

import java.util.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：频繁调用 String.intern() 将动态生成的字符串放入常量池，虽然字符串本身可能被释放，但常量池中的引用被 JVM 永久持有，特别是当这些字符串是大量动态数据时
 * 测试意图：覆盖持有者对象类型为 String.intern 的场景，体现常量池污染导致的引用残留
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    private static final List<String> internedStrings = new ArrayList<>(); // 检查点, 测试因子(holder_object_type=string_intern)

    /**
     * 演示 String.intern() 滥用导致的常量池污染
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {

        // 模拟生成大量动态字符串并 intern
        for (int i = 0; i < 100; i++) {
            // 创建动态字符串（实际应用中可能来自网络、文件等）
            String dynamicString = "dynamic_key_" + UUID.randomUUID().toString();

            // 将其 intern 放入常量池并保存引用
            String internedString = dynamicString.intern();
            internedStrings.add(internedString);
        }
        log.info("Interned {} strings into the constant pool", internedStrings.size());

        // 违规点：这些 intern 过的字符串引用被 internedStrings 列表持有
        // 虽然我们可以清空 internedStrings 列表，但这些字符串已经永久存在于常量池中
        // 时间久了，常量池会不断增长且无法收缩
        // 正确做法：避免对动态生成的字符串调用 intern()，只对少量确定的字符串使用 intern()
    }
}