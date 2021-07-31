package org.xdove.utils;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * test
 * @author Wszl
 * @date 2021年7月31日
 */
public class WebSignatureUtilsTest {

    @Test
    public void testMd5Hash() throws NoSuchAlgorithmException {
        final String secret = "123456";
        final String separator = "&&";
        final String s = WebSignatureUtilsTest.class.getName();
        System.out.println(s);
        byte[] md5s = WebSignatureUtils.hashWithSecret(s, secret, true, separator, MessageDigest.getInstance("MD5"));
        String md5 = Hex.encodeHexString(md5s);
        System.out.println(md5);
    }
}
