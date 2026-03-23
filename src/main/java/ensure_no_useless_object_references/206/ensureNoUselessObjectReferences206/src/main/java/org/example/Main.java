package org.example;

import java.util.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Web会话上下文管理器
 */
class HttpSession {
    private Map<String, Object> attributes = new HashMap<>();

    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
    }

    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    public void removeAttribute(String name) {
        attributes.remove(name);
    }
}

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：Web会话（Session）中存储的数据如果在会话失效时未被清理，会导致会话对象及其关联的用户数据被永久持有
 * 测试意图：覆盖持有者成员的存储作用域为Web会话作用域的场景，体现会话作用域数据未被清理的过期数据持有
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    private static final Map<String, HttpSession> sessionStore = new HashMap<>(); // 检查点, 测试因子(storage_scope=web_session)

    /**
     * 演示Web会话作用域的数据永久持有
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {

        // 模拟用户 A 的会话
        HttpSession sessionA = new HttpSession();
        sessionA.setAttribute("userId", "userA");
        sessionA.setAttribute("loginTime", System.currentTimeMillis());
        sessionA.setAttribute("cartItems", new ArrayList<>());  // 模拟购物车数据
        sessionStore.put("sessionA", sessionA);
        log.info("Created session A with user data");

        // 模拟用户 B 的会话
        HttpSession sessionB = new HttpSession();
        sessionB.setAttribute("userId", "userB");
        sessionB.setAttribute("cartItems", new ArrayList<>());
        sessionStore.put("sessionB", sessionB);
        log.info("Created session B with user data");

        // 模拟用户 A 登出（会话失效）
        // 正确做法应该是：sessionStore.remove("sessionA");

        // 违规点：会话 A 虽然逻辑上已经失效，但 sessionStore 仍然持有它的引用
        // 导致 userA 的所有数据（userId、loginTime、cartItems等）都无法被垃圾回收
        log.info("Session A should be invalidated, but sessionStore still holds reference");
        Object sessionARef = sessionStore.get("sessionA");
        log.info("Session A still exists in store: {}", sessionARef != null);

        // 如果长时间不清理过期会话，sessionStore 会不断积累无用的会话对象
        // 这在高并发场景下会导致严重的内存泄漏
    }
}