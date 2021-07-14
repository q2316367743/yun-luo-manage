package xyz.esion.manage;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * 密码生成
 *
 * @author Esion
 * @since 2021/5/29
 */
public class PasswordTest {

    public static void main(String[] args) {
        System.out.println(new Digester(DigestAlgorithm.MD5).digestHex("123456"));
    }

}
