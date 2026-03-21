package org.example;

import java.util.function.Predicate;
import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：在 Lambda 表达式的方法体中使用 "==" 比较对象引用
 * 测试意图：覆盖语法上下文：Lambda 表达式方法体
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
        Predicate<String> predicate = s -> str1 == str2; // 检查点, 测试因子(syntax_context=lambda_body)
        log.info("result: {}", predicate.test("test"));
    }
}