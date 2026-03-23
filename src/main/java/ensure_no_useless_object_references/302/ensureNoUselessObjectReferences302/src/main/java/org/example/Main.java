package org.example;

import java.util.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：if-else中，其中一条分支执行了清理代码，而另一条分支没有，导致特定路径上的资源被永久持有
 * 测试意图：覆盖编码缺陷类型为if-else的branch_miss缺陷，体现部分路径上清理失败的问题
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    private static final Map<String, Object> resourceCache = new HashMap<>(); // 检查点, 测试因子(coding_defect=branch_miss)

    /**
     * 演示if-else分支不一致的清理逻辑
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        boolean requiresCleanup = loadConfigOption();
        Object resource = new Object();
        resourceCache.put("resource", resource);
        log.info("Resource stored in cache");

        if (requiresCleanup) {
            // 分支1：清理是正常的
            resourceCache.remove("resource");
            log.info("Resource cleaned up in if branch");
        } else {
            // 违规点：else分支中也需要清理，但里面没有清理代码
            log.info("Taking alternative path, but cleanup code is missing!");
            // 资源仍然被永久持有
        }
    }

    private static boolean loadConfigOption() {
        return false; // 模拟配置返回
    }
}