package org.example;

import java.util.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：强引用持有对象，未及时置 null
 * 测试意图：覆盖 引用类型：强引用持有对象
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-22
 */
@Slf4j
public class Main {
    // 故意持有无用引用
    private static Object uselessRef; // 检查点, 测试因子(reference_type=strong_no_null)

    public static void main(String[] args) {
        uselessRef = new Object();
        log.info("viol");
    }
}