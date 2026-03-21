package org.example;

import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：使用 "==" 比较自定义POJO类对象引用
 * 测试意图：覆盖比较对象类型：自定义POJO类
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-21
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        Person p1 = new Person("Alice"); // 测试因子(compared_object_type=custom_pojo)
        Person p2 = new Person("Alice");
        if (p1 == p2) { // 检查点
            log.info("viol");
        }
    }

    /**
     * 自定义POJO类
     */
    static class Person {
        private String name;

        /**
         * 构造方法
         *
         * @param name 人名
         */
        Person(String name) {
            this.name = name;
        }

        /**
         * 获取名称
         *
         * @return 的名称
         */
        public String getName() {
            return name;
        }
    }
}