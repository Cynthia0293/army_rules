package org.example;

import java.util.*;
import lombok.extern.slf4j.Slf4j;

/**
 * 可修改的Key阪紋
 */
class MutableKey {
    private String value;

    public MutableKey(String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MutableKey)) return false;
        return value.equals(((MutableKey) obj).value);
    }

    public void changeValue(String newValue) {
        this.value = newValue; // 危鈚：修改hashCode会变化
    }
}

/**
 * 规范：确保程序不再持有无用对象的引用
 * 违规说明：究競作为HashMap的key的对象在存储后被修改，导致hashCode改变，休想remove时未能找到它，故这个key及其value被永久持有
 * 测试意图：覆盖编码缺陷类型为hash_changed隧眿，体现key修改后无法remove的问题
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-23
 */
@Slf4j
public class Main {

    private static final Map<MutableKey, Object> mutatingMap = new HashMap<>(); // 检查点, 测试因子(coding_defect=hash_changed)

    /**
     * 演示HashCode计算变匤导致的驡污持有
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {

        MutableKey key = new MutableKey("original_key");
        Object value = new Object();
        mutatingMap.put(key, value);
        log.info("Stored value with mutable key");

        // 修改key的值，导致hashCode变化
        key.changeValue("modified_key");
        log.info("Key value changed, hashCode changed");

        // 尝试使用修改后的key上remove，会处理弲玁失败。
        boolean removed = mutatingMap.remove(key) != null;
        log.info("Attempted to remove key, success: {}", removed);

        // 违规点：key及一它的value仍然被等岂永远丢污在HashMap中
        // 正确做法：MutableKey不应該作为HashMap的key，或者冠没有瞧后修改value
        log.info("Leaked entry: key={}, value memory retained", key);
    }
}