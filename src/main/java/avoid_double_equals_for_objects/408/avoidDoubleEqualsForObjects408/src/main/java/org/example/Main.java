package org.example;

import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：在方法调用时传入的参数表达式中使用 "==" 比较对象引用
 * 测试意图：覆盖语法上下文：方法调用参数表达式
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-21
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        String str1 = new String("test");
        String str2 = new String("test");
        logComparison(str1 == str2); // 检查点, 测试因子(syntax_context=method_argument)
    }

    static void logComparison(boolean result) {
        log.info("result: {}", result);
    }
}