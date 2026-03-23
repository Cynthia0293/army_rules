package org.example;

import lombok.extern.slf4j.Slf4j;

import java.util.TreeMap;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：在 if 条件中使用 "==" 比较 树形映射表 (java.util.TreeMap) 对象引用
 * 测试意图：覆盖比较对象类型：树形映射表 (java.util.TreeMap)
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
        TreeMap<Object, Object> coll1 = new TreeMap<>(); // 测试因子(compared_object_type=tree_map)
        TreeMap<Object, Object> coll2 = new TreeMap<>();
        if (coll1 == coll2) { // 检查点
            log.info("viol");
        }
    }
}
