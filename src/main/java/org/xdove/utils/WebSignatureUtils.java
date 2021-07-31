package org.xdove.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class WebSignatureUtils {

    private final static Logger log = LogManager.getLogger(WebSignatureUtils.class);

    /**
     * 将数据进行ascii排序
     * @param p 参数
     * @param sortValues 是否对值进行排序
     * @return 排序后的数据
     */
    public static Map<String, Object> asciiSort(Map<String, Object> p, boolean sortValues) {
        Objects.requireNonNull(p, "param is required.");
        if (p instanceof TreeMap) {
            for (Map.Entry<String, Object> entry : p.entrySet()) {
                if (entry.getValue() instanceof Map && sortValues) {
                    p.put(entry.getKey(), asciiSort((Map<String, Object>) entry.getValue(), sortValues));
                }
            }
            return p;
        } else {
            return new TreeMap<>(p);
        }
    }

    /**
     * 讲map数据组合成字符串，使用http参数的规则。用&连接参数
     * @param p 参数
     * @param ignoreEmpty 是否组合空值数据
     * @return string
     */
    public static String combHttpGetParam(Map<String, String> p, boolean ignoreEmpty) {
        StringBuilder sb = new StringBuilder();
        p.forEach((k, v) -> {
            if (!ignoreEmpty && Objects.isNull(v)) {
                return;
            }
            sb.append(k).append("=").append(v);
            sb.append("&");
        });
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 类似HMAC算法，使用自定义的secret与字符串组合后进行散列
     * @param s 要散列的内容
     * @param secret 安全密钥
     * @param isPrefix true 密钥前置组合内容，false 密钥后置组合内容
     * @param separator 分隔符
     * @param messageDigest 散列算法
     * @return 散列值
     */
    public static byte[] hashWithSecret(String s, String secret, boolean isPrefix, String separator, MessageDigest messageDigest) {
        StringBuilder sb = new StringBuilder();
        if (isPrefix) {
            sb.append(secret).append(separator).append(s);
        } else {
            sb.append(s).append(separator).append(secret);
        }
        if (log.isDebugEnabled()) {
            log.debug("hash string is [{}]", sb);
        }
        return messageDigest.digest(sb.toString().getBytes());
    }

}
