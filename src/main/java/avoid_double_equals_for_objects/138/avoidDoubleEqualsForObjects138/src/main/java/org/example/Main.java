package org.example;


import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：在 if 条件中使用 "==" 比较 自定义业务实体类 (Custom POJO) 对象引用
 * 测试意图：覆盖比较对象类型：自定义业务实体类 (Custom POJO)
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-21
 */
@Slf4j
public class Main {
    /**
     * 主方法，执行程序入口。
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        class Person {
            String name;

            Person(String name) {
                this.name = name;
            }
        }
        Person p1 = new Person("test"); // 测试因子(compared_object_type=custom_pojo)
        Person p2 = new Person("test");
        log.info("p1.name: {}, p2.name: {}", p1.name, p2.name);
        if (p1 == p2) { // 检查点
            log.info("viol");
        }
    }
}
