package org.example;

import java.util.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：在 if 条件中使用 "==" 比较 动态数组集合 (java.util.ArrayList) 对象引用
 * 测试意图：覆盖比较对象类型：动态数组集合 (java.util.ArrayList)
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
        ArrayList coll1 = new ArrayList(); // 测试因子(compared_object_type=array_list)
        ArrayList coll2 = new ArrayList();
        if (coll1 == coll2) { // 检查点
            log.info("viol");
        }
    }
}
