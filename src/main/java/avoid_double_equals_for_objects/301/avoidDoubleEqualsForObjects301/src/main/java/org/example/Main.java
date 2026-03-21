package org.example;

import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：使用 "==" 比较显式使用 new 关键字创建的对象引用
 * 测试意图：覆盖对象实例化来源：显式使用 new 关键字创建对象
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-21
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        String str1 = new String("test"); // 测试因子(instantiation_source=explicit_new)
        String str2 = new String("test");
        if (str1 == str2) { // 检查点
            log.info("viol");
        }
    }
}