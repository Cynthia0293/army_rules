package org.example;

import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：在 if 条件中使用 "==" 比较 HashMap 对象引用
 * 测试意图：覆盖比较对象类型：java.util.HashMap 哈希映射表
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-21
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        HashMap<String, String> map1 = new HashMap<>(); // 测试因子(compared_object_type=hash_map)
        map1.put("key", "test");
        HashMap<String, String> map2 = new HashMap<>();
        map2.put("key", "test");
        if (map1 == map2) { // 检查点
            log.info("viol");
        }
    }
}