package org.example;

import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：使用 "==" 比较 float[] 数组对象引用
 * 测试意图：覆盖比较对象类型：float[] 数组
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-21
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        float[] arr1 = new float[]{1.0f, 2.0f, 3.0f}; // 测试因子(compared_object_type=float_array)
        float[] arr2 = new float[]{1.0f, 2.0f, 3.0f};
        if (arr1 == arr2) { // 检查点
            log.info("viol");
        }
    }
}