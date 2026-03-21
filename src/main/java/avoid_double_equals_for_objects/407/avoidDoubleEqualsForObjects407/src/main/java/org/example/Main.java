package org.example;

import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：在方法 return 语句的返回值表达式中使用 "==" 比较对象引用
 * 测试意图：覆盖语法上下文：return 语句返回值表达式
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
        boolean result = checkEqual(str1, str2);
        log.info("result: {}", result);
    }

    static boolean checkEqual(String s1, String s2) {
        return s1 == s2; // 检查点, 测试因子(syntax_context=return_statement)
    }
}