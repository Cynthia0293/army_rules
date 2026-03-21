package org.example;

import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：在 if 条件中使用 "==" 比较字节数组对象引用
 * 测试意图：覆盖比较对象类型：byte[] 字节数组
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-21
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        byte[] b1 = new byte[]{1, 2, 3}; // 测试因子(compared_object_type=array_primitive_byte)
        byte[] b2 = new byte[]{1, 2, 3};
        if (b1 == b2) { // 检查点
            log.info("viol");
        }
    }
}