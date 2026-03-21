package org.example;

import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：在 if 条件中使用 "==" 比较 Character 包装类对象引用
 * 测试意图：覆盖比较对象类型：Character 包装类
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-21
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        Character c1 = new Character('a'); // 测试因子(compared_object_type=wrapper_character)
        Character c2 = new Character('a');
        if (c1 == c2) { // 检查点
            log.info("viol");
        }
    }
}