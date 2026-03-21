package org.example;

import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：在 if 条件中使用 "==" 比较 BigDecimal 对象引用
 * 测试意图：覆盖比较对象类型：BigDecimal 高精度浮点数
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-21
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        BigDecimal bd1 = new BigDecimal("1.23"); // 测试因子(compared_object_type=big_decimal)
        BigDecimal bd2 = new BigDecimal("1.23");
        if (bd1 == bd2) { // 检查点
            log.info("viol");
        }
    }
}