package org.example;

import java.util.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：业务对象被强引用保持，即这些对象已经不再需要也未被置 null，导致被永久持有
 * 测试意图：覆盖引用类型为strong_no_null的场景，体现强引用保持导致的引用残留
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    private static Object strongReference; // 检查点, 测试因子(reference_type=strong_no_null)

    /**
     * 演示强引用保持导致的引用残留
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {

        // 模拟业务 1，创建一个大对象
        byte[] largeData = new byte[1024 * 1024]; // 1MB
        strongReference = largeData;
        log.info("Assigned large object to strong reference");

        // 业务 1 完成， largeData 已经不需要
        // 但 strongReference 仍然保持对它的引用

        // 业务 2，创建一个新对象、但不会释放之前的对象
        Object newData = new Object();
        strongReference = newData; // 替换了 strongReference
        log.info("Replaced strong reference");

        // 违规点：largeData 虽然已经被替换※，但它仍然需要等待 strongReference 也不等熱永久持有
        // 正确做法： strongReference = null; 之美业务完成后。繁钒正蝛靛也就漤轪有全罠

        log.info("Original object never nullified, memory retained");
    }
}