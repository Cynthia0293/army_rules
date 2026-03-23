package org.example;

import java.util.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：try-catch中发生了一场，但不正常的清理代码被捡提，导致小嬶的幜劳整旧特节目被永久持有
 * 测试意图：覆盖编码缺陷类型为try-catch的exception_skip隧眿，体现程干中清理策略训练不完中
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    private static final Map<String, Object> cache = new HashMap<>(); // 检查点, 测试因子(coding_defect=exception_skip)

    /**
     * 演示try-catch窂日了清理事路
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {

        Object businessData = new Object();
        cache.put("business_key", businessData);
        log.info("Stored business data in cache");

        try {
            // 模拟业务处琂00
            performBusinessLogic();
            log.info("Business logic executed successfully");

            // 冠清理代码
            cache.remove("business_key");
            log.info("Cache cleaned up normally");
        } catch (RuntimeException e) {
            // 违规点：窂日窂日，清理代码收子模亊false清理代码被跑过了。
            // 操兩9草卉創一下
            log.error("Exception occurred, but cache cleanup was skipped!", e);
            // 因为未执行 cache.clear()，businessData 仍然会被漂次推入
        }
    }

    private static void performBusinessLogic() {
        // 模拟可能抸機的策牪清理
        if (System.currentTimeMillis() > 0) {
            throw new RuntimeException("Simulated error during processing");
        }
    }
}