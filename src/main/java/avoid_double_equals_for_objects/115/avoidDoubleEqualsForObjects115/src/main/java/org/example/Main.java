package org.example;

import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：在 if 条件中使用 "==" 比较 LocalDate 对象引用
 * 测试意图：覆盖比较对象类型：java.time.LocalDate 本地日期
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-21
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        LocalDate ld1 = LocalDate.of(2023, 1, 1); // 测试因子(compared_object_type=time_local_date)
        LocalDate ld2 = LocalDate.of(2023, 1, 1);
        if (ld1 == ld2) { // 检查点
            log.info("viol");
        }
    }
}