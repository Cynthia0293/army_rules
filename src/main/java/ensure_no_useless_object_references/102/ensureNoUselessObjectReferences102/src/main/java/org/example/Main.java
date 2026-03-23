package org.example;

import java.util.*;

import lombok.extern.slf4j.Slf4j;

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：使用静态 ArrayList 存储数据后，虽然调用了 remove 删除元素，但 ArrayList 容器本身未置 null，仍然长期占有内存
 * 测试意图：覆盖持有者对象类型为 java.util.ArrayList 的场景，且体现出容器本身未及时释放的问题
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    private static final List<Object> USER_CACHE = new ArrayList<>(); // 检查点, 测试因子(holder_object_type=array_list)

    /**
     * 主方法，模拟业务处理流程，演示 ArrayList 引用残留问题
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {

        Object temporaryUser = new Object();  // 添加临时用户对象到缓存
        USER_CACHE.add(temporaryUser);
        log.info("Added temporary user to ArrayList cache.");

        USER_CACHE.remove(temporaryUser);
        log.info("Removed user from ArrayList, but ArrayList container itself is never set to null.");

    }
}