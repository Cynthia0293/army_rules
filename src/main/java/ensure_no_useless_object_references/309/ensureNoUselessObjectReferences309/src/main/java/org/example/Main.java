package org.example;

import java.util.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 全局事件监听器注册中心
 */
class EventRegistry {
    private static final List<Object> listeners = new ArrayList<>();

    public static void register(Object listener) {
        listeners.add(listener);
        org.slf4j.LoggerFactory.getLogger(EventRegistry.class).info("Listener registered");
    }
}

/**
 * 业务对象：在构造器中进行this逃逸
 */
class BusinessService {
    private byte[] largeData = new byte[1024 * 1024]; // 1MB

    public BusinessService() {
        // 危险：在构造器中将this注册到全局注册表，导致this逃逸
        EventRegistry.register(this); // 检查点, 测试因子(coding_defect=implicit_this_escape)
        org.slf4j.LoggerFactory.getLogger(BusinessService.class).info("BusinessService instance registered");
    }
}

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：在对象的构造器中将this注册到全局容器中，导致未完成构造的对象逃逸到其他作用域，该对象及其所有成员被永久持有
 * 测试意图：覆盖编码缺陷类型为implicit_this_escape缺陷，体现构造器中this逃逸导致的不可控引用持有
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    /**
     * 演示构造器中this逃逸
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {

        // 创建业务服务对象
        // 在构造器执行期间，this已经被注册到全局注册表中
        BusinessService service = new BusinessService();
        log.info("BusinessService instance created");

        // 即使service对象本地引用被清理，全局注册表中的引用仍然存在
        service = null;
        log.info("Local reference cleared, but global registry still holds reference");

        // 违规点：BusinessService对象及其1MB的largeData被EventRegistry永久持有
        // 正确做法：
        // 1. 不要在构造器中注册this
        // 2. 提供独立的初始化方法，在对象完全构造后调用
        // 3. 或使用工厂方法模式
    }
}