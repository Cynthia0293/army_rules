package org.example;

import java.util.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：WeakHashMap 被强引用间接持有，弱引用失效
 * 测试意图：覆盖 引用类型：weak_hash_map
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-22
 */
@Slf4j
public class Main {
    // 故意持有无用引用
    private static Object uselessRef; // 检查点, 测试因子(reference_type=weak_map_strong_hold)

    public static void main(String[] args) {
        uselessRef = new Object();
        log.info("viol");
    }
}