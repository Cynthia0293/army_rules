package org.example;

import java.util.*;

import lombok.extern.slf4j.Slf4j;

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：使用静态 Stack 存储对象后，虽然通过 pop() 或 remove() 清空了元素，但 Stack 容器本身作为静态成员未置 null，导致容器长期被持有
 * 测试意图：覆盖持有者对象类型为 java.util.Stack 的场景，并演示容器虽然元素被删除但容器本身仍被长期持有的问题
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    private static final Stack<Object> OPERATION_STACK = new Stack<>(); // 检查点, 测试因子(holder_object_type=stack)

    /**
     * 主方法，模拟操作栈的使用场景
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {

        Object operation = new Object(); // 将操作入栈
        OPERATION_STACK.push(operation);
        log.info("Operation pushed to static Stack.");


        OPERATION_STACK.pop();
        log.info("Operation popped from Stack, but Stack container itself is never nullified.");

    }
}