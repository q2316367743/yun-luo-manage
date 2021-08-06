package xyz.esion.manage;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * @author Esion
 * @since 2021/8/6
 */
public class StringTest {

    public static void main(String[] args) {
        // e10adc3949ba59abbe56e057f20f883e
        System.out.println(new Digester(DigestAlgorithm.MD5).digestHex("123456"));
    }

}
