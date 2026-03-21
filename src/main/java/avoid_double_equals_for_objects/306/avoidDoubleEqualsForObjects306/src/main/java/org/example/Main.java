package org.example;

import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：使用 "==" 比较通过反射机制实例化的对象引用
 * 测试意图：覆盖对象实例化来源：通过反射机制实例化对象
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-21
 */
@Slf4j
public class Main {
    public static void main(String[] args) throws Exception {
        String str1 = "test";
        String str2 = (String) Class.forName("java.lang.String")
            .getConstructor(String.class)
            .newInstance("test"); // 测试因子(instantiation_source=reflection)
        if (str1 == str2) { // 检查点
            log.info("viol");
        }
    }
}