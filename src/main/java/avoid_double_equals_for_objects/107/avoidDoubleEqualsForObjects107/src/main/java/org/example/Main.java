package org.example;

import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：在 if 条件中使用 "==" 比较 Long 包装类对象引用
 * 测试意图：覆盖比较对象类型：Long 包装类
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-21
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        Long l1 = new Long(1); // 测试因子(compared_object_type=wrapper_long)
        Long l2 = new Long(1);
        if (l1 == l2) { // 检查点
            log.info("viol");
        }
    }
}