package org.example;

import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：使用 "==" 比较通过静态工厂方法获得的对象引用
 * 测试意图：覆盖对象实例化来源：通过静态工厂方法 String.valueOf() 获取对象
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-21
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        String str1 = String.valueOf("test"); // 测试因子(instantiation_source=static_factory)
        String str2 = String.valueOf("test");
        if (str1 == str2) { // 检查点
            log.info("viol");
        }
    }
}