package org.example;

import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：在 if 条件中使用 "==" 比较 Double 包装类对象引用
 * 测试意图：覆盖比较对象类型：Double 包装类
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-21
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        Double d1 = new Double(1.0); // 测试因子(compared_object_type=wrapper_double)
        Double d2 = new Double(1.0);
        if (d1 == d2) { // 检查点
            log.info("viol");
        }
    }
}