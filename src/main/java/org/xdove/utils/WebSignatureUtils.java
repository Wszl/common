package org.xdove.utils;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class WebSignatureUtils {

    /**
     * 将数据进行ascii排序
     * @param p 参数
     * @return 排序后的数据
     */
    public static Map<String, String> asciiSort(Map<String, String> p) {
        if (p instanceof TreeMap) {
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


}
