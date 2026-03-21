package org.example;

import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：在 if 条件中使用 "==" 比较 Short 包装类对象引用
 * 测试意图：覆盖比较对象类型：Short 包装类
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-21
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        Short s1 = new Short((short) 1); // 测试因子(compared_object_type=wrapper_short)
        Short s2 = new Short((short) 1);
        if (s1 == s2) { // 检查点
            log.info("viol");
        }
    }
}