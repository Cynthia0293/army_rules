package org.example;


import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：在 if 条件中使用 "==" 比较通过 通过对象克隆获取 (Object.clone) 获取的 String 对象引用
 * 测试意图：覆盖实例化来源：通过对象克隆获取 (Object.clone)
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-21
 */
@Slf4j
public class Main {
    /**
     * 主方法，执行程序入口。
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        class CloneableData implements Cloneable {
            String data;
            CloneableData(String data) { this.data = data; }
            @Override
            public Object clone() throws CloneNotSupportedException {
                return super.clone();
            }
        }
        try {
            CloneableData obj1 = new CloneableData("test"); // 测试因子(instantiation_source=clone)
            CloneableData obj2 = (CloneableData) obj1.clone();
            if (obj1 == obj2) { // 检查点
                log.info("viol");
            }
        } catch (CloneNotSupportedException e) {
            log.error("Clone failed", e);
        }
    }
}
