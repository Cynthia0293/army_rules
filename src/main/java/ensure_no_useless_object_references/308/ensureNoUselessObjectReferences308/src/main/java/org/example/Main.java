package org.example;

import java.io.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 资源包装类（模拟）
 */
class ResourceWrapper implements Closeable {
    @SuppressWarnings("unused")
    private byte[] buffer = new byte[1024 * 1024]; // 1MB缓冲区

    public void process() {
        log.info("Processing resource...");
    }

    @Override
    public void close() throws IOException {
        log.info("Resource closed");
    }

    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ResourceWrapper.class);
}

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：资源包装类（如文件流、数据库连接等）在使用完毕后未被显式关闭，导致资源及其关联的缓冲区被永久持有
 * 测试意图：覆盖编码缺陷类型为resource_not_closed缺陷，体现资源泄漏导致的引用残留
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    /**
     * 演示资源未关闭的引用残留
     *
     * @param args 命令行参数
     */
    @SuppressWarnings("resource")
    public static void main(String[] args) {

        // 创建资源
        ResourceWrapper resource = new ResourceWrapper(); // 检查点, 测试因子(coding_defect=resource_not_closed)
        log.info("Resource created");

        try {
            // 使用资源
            resource.process();
        } catch (Exception e) {
            log.error("Error during processing", e);
        }

        // 违规点：资源从未被关闭
        // 正确做法应该是：
        // 1. try-with-resources: try (ResourceWrapper r = new ResourceWrapper()) { r.process(); }
        // 2. 或手动close: resource.close();
        // 3. 或finally block确保close

        log.info("Resource never closed, buffer and other resources retained");

        // 如果在长生命周期应用中频繁创建此类未关闭的资源，会导致严重的内存泄漏
    }
}