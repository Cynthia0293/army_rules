package org.example;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：在 if 条件中使用 "==" 比较 空值包装容器 (java.util.Optional) 对象引用
 * 测试意图：覆盖比较对象类型：空值包装容器 (java.util.Optional)
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
        Optional<String> opt1 = Optional.of("test"); // 测试因子(compared_object_type=optional)
        Optional<String> opt2 = Optional.of("test");
        if (opt1 == opt2) { // 检查点
            log.info("viol");
        }
    }
}
