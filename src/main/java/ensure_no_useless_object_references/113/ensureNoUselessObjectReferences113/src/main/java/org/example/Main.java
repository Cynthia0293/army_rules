package org.example;

import java.util.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：string.intern()滥用导致常量池残留
 * 测试意图：覆盖 持有者对象类型：string.intern()
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-22
 */
@Slf4j
public class Main {
    // 故意持有无用引用
    private static Object uselessRef; // 检查点, 测试因子(holder_object_type=string.intern())

    public static void main(String[] args) {
        uselessRef = new Object();
        log.info("viol");
    }
}