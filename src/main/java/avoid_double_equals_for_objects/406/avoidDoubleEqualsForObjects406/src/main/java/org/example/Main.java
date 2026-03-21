package org.example;

import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：在变量赋值操作的右值表达式中使用 "==" 比较对象引用
 * 测试意图：覆盖语法上下文：变量赋值右值表达式
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
        boolean result = str1 == str2; // 检查点, 测试因子(syntax_context=variable_assignment)
        log.info("result: {}", result);
    }
}