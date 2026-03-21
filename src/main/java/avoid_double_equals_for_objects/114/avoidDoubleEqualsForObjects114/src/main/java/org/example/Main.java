package org.example;

import java.util.Date;
import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：在 if 条件中使用 "==" 比较 Date 对象引用
 * 测试意图：覆盖比较对象类型：java.util.Date 传统日期
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-21
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        Date d1 = new Date(1000); // 测试因子(compared_object_type=util_date)
        Date d2 = new Date(1000);
        if (d1 == d2) { // 检查点
            log.info("viol");
        }
    }
}