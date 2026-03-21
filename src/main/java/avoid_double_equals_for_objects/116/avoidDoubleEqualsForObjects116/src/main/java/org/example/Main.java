package org.example;

import java.time.LocalTime;
import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：在 if 条件中使用 "==" 比较 LocalTime 对象引用
 * 测试意图：覆盖比较对象类型：java.time.LocalTime 本地时间
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-21
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        LocalTime lt1 = LocalTime.of(10, 30, 40); // 测试因子(compared_object_type=time_local_time)
        LocalTime lt2 = LocalTime.of(10, 30, 40);
        if (lt1 == lt2) { // 检查点
            log.info("viol");
        }
    }
}