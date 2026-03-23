package org.example;

import java.util.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：集合中的所有元素被清空后，集合容器本身作为静态成员未被置为null，导致虽然元素被释放但容器本身仍占用内存
 * 测试意图：覆盖编码缺陷类型为container_not_null缺陷，体现集合清空后容器本身未释放的问题
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    private static List<Object> dataList = new ArrayList<>(); // 检查点, 测试因子(coding_defect=container_not_null)

    /**
     * 演示集合清空后容器未置null的引用残留
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {

        // 添加数据到集合
        for (int i = 0; i < 1000; i++) {
            dataList.add(new Object());
        }
        log.info("Added 1000 objects to list");

        // 处理数据...
        log.info("List size: {}", dataList.size());

        // 清空集合
        dataList.clear();
        log.info("List cleared");

        // 违规点：集合本身dataList虽然被清空但未置null
        // 作为静态成员，它仍占用内存，且ArrayList的内部数组仍被持有
        // 正确做法：dataList = null; 或者用try-finally来管理
        log.info("List container itself is never set to null, memory retained");
    }
}