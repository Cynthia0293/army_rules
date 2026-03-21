package org.example;


import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：在 方法 return 语句的返回值表达式中 使用 "==" 比较 String 对象引用
 * 测试意图：覆盖语法上下文：方法 return 语句的返回值表达式中
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-21
 */
@Slf4j
public class Main {
    /**
     * 主方法，执行程序入口。
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        String str1 = new String("test");
        String str2 = new String("test");
        boolean result = compare(str1, str2);
        log.info("result: {}", result);
    }

    /**
     * 比较方法
     * @param s1 字符串1
     * @param s2 字符串2
     * @return 比较结果
     */
    private static boolean compare(String s1, String s2) {
        return s1 == s2; // 检查点, 测试因子(syntax_context=return_statement)
    } 
}


