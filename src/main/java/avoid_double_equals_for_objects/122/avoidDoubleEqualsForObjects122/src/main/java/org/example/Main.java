package org.example;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：在 if 条件中使用 "==" 比较 链表集合 (java.util.LinkedList) 对象引用
 * 测试意图：覆盖比较对象类型：链表集合 (java.util.LinkedList)
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
        LinkedList<Object> coll1 = new LinkedList<>(); // 测试因子(compared_object_type=linked_list)
        LinkedList<Object> coll2 = new LinkedList<>();
        if (coll1 == coll2) { // 检查点
            log.info("viol");
        }
    }
}
