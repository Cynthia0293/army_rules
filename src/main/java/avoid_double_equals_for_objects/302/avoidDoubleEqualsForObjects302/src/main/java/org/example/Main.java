package org.example;

import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：使用 "==" 比较通过自动装箱获得的对象引用
 * 测试意图：覆盖对象实例化来源：基本类型自动装箱
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-21
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        Integer i1 = 1; // 测试因子(instantiation_source=autoboxing)
        Integer i2 = 1;
        for (int i = 0; i < 1 && i1 == i2; i++) { // 检查点
            log.info("viol");
        }
    }
}