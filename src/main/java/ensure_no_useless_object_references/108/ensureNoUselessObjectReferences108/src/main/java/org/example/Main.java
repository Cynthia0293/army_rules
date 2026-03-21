package org.example;

import java.util.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：非静态内部类隐式持有外部类
 * 测试意图：覆盖 持有者对象类型：非静态内部类隐式持有外部类 non_static_inner_class
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-22
 */
@Slf4j
public class Main {
    // 故意持有无用引用
    private static Object uselessRef; // 检查点, 测试因子(holder_object_type=non_static_inner_class)

    public static void main(String[] args) {
        uselessRef = new Object();
        log.info("viol");
    }
}