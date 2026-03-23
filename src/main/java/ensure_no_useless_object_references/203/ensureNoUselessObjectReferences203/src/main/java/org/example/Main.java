package org.example;

import java.util.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 全局应用会话上下文注册表
 */
class ServiceRegistry {
    private static final Map<String, Object> registry = new HashMap<>(); // 检查点, 测试因子(storage_scope=global_registry)

    public static void register(String serviceName, Object service) {
        registry.put(serviceName, service);
        org.slf4j.LoggerFactory.getLogger(ServiceRegistry.class).info("Registered service: {}", serviceName);
    }

    public static Object lookup(String serviceName) {
        return registry.get(serviceName);
    }

    public static void unregister(String serviceName) {
        registry.remove(serviceName);
    }
}

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：业务对象注册到全局服务管理器中，即使业务已经处理完毕也不会从注册表中移除，导致业务数据被永久保存
 * 测试意图：覆盖持有者成员的存储作用域为全局事务表的场景，体现全局注册表污染导致的数据持有
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    /**
     * 演示全局注册表数据污染
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {

        // 模拟永久业务
        Object permanentService = new Object();
        ServiceRegistry.register("permanent-service", permanentService);
        log.info("Registered permanent service");

        // 模拟临时业务
        Object temporaryService = new Object();
        ServiceRegistry.register("temporary-service", temporaryService);
        log.info("Registered temporary service");

        // 业务处理...
        Object retrievedService = ServiceRegistry.lookup("temporary-service");
        log.info("Retrieved service: {}", retrievedService != null);

        // 违规点：业务处理完毕后，临时业务从未从注册表中移除
        // 正确做法：业务完成后应该调用 ServiceRegistry.unregister("temporary-service")
        // 否则注册表会不断积累无用数据，最终导致内存泄漏
    }
}