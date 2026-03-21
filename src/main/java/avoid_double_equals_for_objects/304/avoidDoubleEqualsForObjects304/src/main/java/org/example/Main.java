package org.example;

import lombok.extern.slf4j.Slf4j;

/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：使用 "==" 比较通过对象克隆获取的对象引用
 * 测试意图：覆盖对象实例化来源：通过对象克隆获取
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-21
 */
@Slf4j
public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        CloneableData data1 = new CloneableData("test"); // 测试因子(instantiation_source=clone)
        CloneableData data2 = (CloneableData) data1.clone();
        if (data1 == data2) { // 检查点
            log.info("viol");
        }
    }

    /**
     * 可克隆的数据类
     */
    static class CloneableData implements Cloneable {
        private String value;

        /**
         * 构造方法
         *
         * @param value 值
         */
        CloneableData(String value) {
            this.value = value;
        }

        /**
         * 克隆方法
         *
         * @return 克隆副本
         * @throws CloneNotSupportedException 异常
         */
        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        /**
         * 获取值
         *
         * @return 值
         */
        public String getValue() {
            return value;
        }
    }
}