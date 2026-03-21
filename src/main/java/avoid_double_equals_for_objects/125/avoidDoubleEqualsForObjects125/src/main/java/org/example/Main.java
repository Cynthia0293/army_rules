package org.example;

import java.util.TreeSet;
import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：在 if 条件中使用 "==" 比较 TreeSet 对象引用
 * 测试意图：覆盖比较对象类型：java.util.TreeSet 树形集合
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-21
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        TreeSet<String> set1 = new TreeSet<>(); // 测试因子(compared_object_type=tree_set)
        set1.add("test");
        TreeSet<String> set2 = new TreeSet<>();
        set2.add("test");
        if (set1 == set2) { // 检查点
            log.info("viol");
        }
    }
}