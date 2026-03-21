package org.example;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：在 if 条件中使用 "==" 比较 ArrayList 对象引用
 * 测试意图：覆盖比较对象类型：java.util.ArrayList 动态数组集合
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-21
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        ArrayList<String> list1 = new ArrayList<>(); // 测试因子(compared_object_type=array_list)
        list1.add("test");
        ArrayList<String> list2 = new ArrayList<>();
        list2.add("test");
        if (list1 == list2) { // 检查点
            log.info("viol");
        }
    }
}