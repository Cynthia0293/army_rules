package org.example;


import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：在 if 条件中使用 "使用双等号判断相等 (==)" 比较 String 对象引用
 * 测试意图：覆盖比较操作符：使用双等号判断相等 (==)
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-21
 */
@Slf4j
public class Main {
    /**
     * 主方法，执行程序入口。
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        String str1 = "test";
        String str2 = "test";
        log.info("str1: {}, str2: {}", str1, str2);
        if (str1 == str2) { // 检查点, 测试因子(comparison_operator=equals)
            log.info("viol");
        }
    }
}
