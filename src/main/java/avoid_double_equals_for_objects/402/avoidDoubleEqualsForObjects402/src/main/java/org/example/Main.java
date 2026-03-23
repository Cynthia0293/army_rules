package org.example;

import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：在 while 循环的条件表达式中 使用 "==" 比较 String 对象引用
 * 测试意图：覆盖语法上下文：while 循环的条件表达式中
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
        String target = "test";
        String heapString = new StringBuilder("te").append("st").toString();
        String[] data = {"test", "test", heapString};
        int i = 0;

        while (i < data.length && data[i] == target) { // 检查点, 测试因子(syntax_context=while_condition)
            log.info("viol");
            i++;
        }
    }
}