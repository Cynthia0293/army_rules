package org.example;


import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：在 if 条件中使用 "==" 比较通过 通过反射机制实例化对象 获取的 String 对象引用
 * 测试意图：覆盖实例化来源：通过反射机制实例化对象
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-21
 */
@Slf4j
public class Main {
    /**
     * 主方法，执行程序入口。
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        try {
            String str1 = String.class.newInstance(); // 测试因子(instantiation_source=reflection)
            String str2 = String.class.newInstance();
            if (str1 == str2) { // 检查点
                log.info("viol");
            }
        } catch (Exception e) {
            log.error("Reflection failed", e);
        }
    }
}
