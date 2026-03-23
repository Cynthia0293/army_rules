package org.example;


import lombok.extern.slf4j.Slf4j;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * 规范：对象比较必须使用 equals 而不是 "=="
 * 违规说明：在 if 条件中使用 "==" 比较通过 通过反序列化获取对象 获取的 String 对象引用
 * 测试意图：覆盖实例化来源：通过反序列化获取对象
 *
 * @author 曹卓熠
 * @version 1.0.0
 * @since 2026-03-21
 */
@Slf4j
public class Main {
    /**
     * 主方法，执行程序入口。
     *
     * @param args 命令行参数
     */

    public static void main(String[] args) throws Exception {

        String original = "test_data";

        byte[] serializedData = serialize(original);

        String deserializedStr = (String) deserialize(serializedData); // 测试因子(instantiation_source=deserialization)

        String target = "test_data";

        if (deserializedStr == target) { // 检查点
            log.info("viol");
        }
    }

    private static byte[] serialize(String value) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(value);
            objectOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        }
    }

    private static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            return objectInputStream.readObject();
        }
    }
}

