package org.example;

import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：使用 "==" 操作符进行对象相等性比较
 * 测试意图：覆盖比较操作符：== 操作符
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
        if (str1 == str2) { // 检查点, 测试因子(comparison_operator=equals)
            log.info("viol");
        }
    }
}