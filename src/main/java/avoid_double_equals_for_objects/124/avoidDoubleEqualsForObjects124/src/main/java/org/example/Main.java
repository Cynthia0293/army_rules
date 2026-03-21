package org.example;

import java.util.HashSet;
import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：在 if 条件中使用 "==" 比较 HashSet 对象引用
 * 测试意图：覆盖比较对象类型：java.util.HashSet 哈希集合
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-21
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        HashSet<String> set1 = new HashSet<>(); // 测试因子(compared_object_type=hash_set)
        set1.add("test");
        HashSet<String> set2 = new HashSet<>();
        set2.add("test");
        if (set1 == set2) { // 检查点
            log.info("viol");
        }
    }
}