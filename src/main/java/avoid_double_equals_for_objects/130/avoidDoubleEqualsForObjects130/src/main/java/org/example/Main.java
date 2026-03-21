package org.example;

import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：使用 "==" 比较 int[] 数组对象引用
 * 测试意图：覆盖比较对象类型：int[] 数组
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-21
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        int[] arr1 = new int[]{1, 2, 3}; // 测试因子(compared_object_type=int_array)
        int[] arr2 = new int[]{1, 2, 3};
        if (arr1 == arr2) { // 检查点
            log.info("viol");
        }
    }
}