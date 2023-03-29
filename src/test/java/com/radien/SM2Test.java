package com.radien;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.BCUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;


/**
 * @author: JiangJi
 * @Descriotion:
 * @Date:Created in 2023/2/25 12:23
 */
@Slf4j
public class SM2Test {

    @Test
    public void test66() throws Exception{
        String url = "https://channel.cheryfs.cn/archer/activity-api/weixin/proxyCallbackWxoepn";
        String key = "0424b85057428c4edb02226d318832eccffdca8f3390d1499511fa3dcec46f22e9db9f56afae597c32bc86f9608edd02b4a44a13a3a2cc0cccb02dba0fe2dd7699";
        log.info(encryptHex(key, url));
        log.info(encryptHex(key, url));
        // 663e150c446ff46e95478225e59fff463a6e353a3d0b77932643f716fd14dbe68bcc09ec2270df7b358172baf2a5839e9ed763b7f18ef3088c71bd89c241e7884e7b4b1364a20e0ef7134739df26361ca73642093ad7751a6fa65132a25af9c0fed148212291056edf328a8e16c39bbd4a4b6f217f072b6ea214d6a944a5243a6f896d961246526c6ddd24ff9ddbf0f3cd421e22d7d0c4936ba7f850673c04191acd37c6660be9f20b
    }

    @Test
    public void test65() throws Exception{
        String key = "04d1a678f31aa75f623cea92ae906539b2423582c81c4e8e81d3a0f8c52f722d2acffc436cd70ab77d7798e6b36e3695a5640cbf53a519f110f5b4a69662bc02e6";
        String plaintext = "7515e40ac546e6a5e40d7902267643fd6a9dcb76b179c31db32783645bb51f679c7583a9fd36a3fb4042093565ee9c631a87a6d581a476117ba2e19f4b8bb2e9faaec6356980fa92178c2ce82cbaffccef7537fbf53f44894e9f15765885ec35b3162f16addef12900163a1a54716469c123e083848f9498528cc8cc09f3dc6875b1f81728ab5cb69f88398a915b06ad38ae343862390f14e8e1cdb5ca5e0e5f93f75c3257f128148f91504ab781c2f21738720fe58e16a78eec5ac754d3115fd588a6fb3ca7720df1f5e3d8c8d31933a09a9a1716fef2b1f1500335e2f81d13d70681c30d73e4f9f87e1503d48ab695b032b343b3ff2bd6f51214c13c3bc64076b1701444004c83466ae2d9f9b026810be32b969ede90c7089ce45e0e5d660151d2b50b3adb61d9bf62b9531d1b92286436313ed4cff323c950d57f039e61d66f01fa0f7548e3cec47773cc10191a272c8590e91383cfc36e5fda609e538997850cd23ca0db5346bbf59bd4cb30c74d078cc030a3a224e43a74d27191d1b825932dc145154ee641557e6bcdc4b3139a477b5de888af63272fd39f94de0a73ae6b049bc61edb5eb6fbd009e5a444cb3f9d83f308d535b7d04a23b3cb051c03bdcb120b563617626020ca9ea2008ccc57f9bb631404f639b9bbdd1ff5df7e1bf88e5c";
        log.info(decryptStr(key, plaintext));
        // 663e150c446ff46e95478225e59fff463a6e353a3d0b77932643f716fd14dbe68bcc09ec2270df7b358172baf2a5839e9ed763b7f18ef3088c71bd89c241e7884e7b4b1364a20e0ef7134739df26361ca73642093ad7751a6fa65132a25af9c0fed148212291056edf328a8e16c39bbd4a4b6f217f072b6ea214d6a944a5243a6f896d961246526c6ddd24ff9ddbf0f3cd421e22d7d0c4936ba7f850673c04191acd37c6660be9f20b
    }


    /**
     * 加密SM2
     * @param publicKey 公钥
     * @param plaintext 明文
     * @return 密文
     */
    public static String encryptHex(String publicKey, String plaintext) {
        // 加密
        SM2 sm2 = SmUtil.sm2(null, publicKey);
        sm2.setMode(SM2Engine.Mode.C1C3C2);
        // 返回值的字符串，需要注意此处加密后密文为16进制，否则前端解密前需要先转换格式
        String encryptStr = sm2.encryptHex(new String(plaintext), Charset.forName("utf-8"), KeyType.PublicKey);
        return encryptStr;
    }


    /**
     * 解密SM2
     * @param privateKey 公钥
     * @param plaintext 明文
     * @return 密文
     */
    public static String decryptStr(String privateKey, String plaintext) {
        if (!plaintext.startsWith("04")) {
            plaintext = "04" + plaintext;
        }
        // 加密
        SM2 sm2 = SmUtil.sm2(privateKey, null);
        sm2.setMode(SM2Engine.Mode.C1C3C2);
        // 返回值的字符串，需要注意此处加密后密文为16进制，否则前端解密前需要先转换格式
        String dencryptStr = sm2.decryptStr(plaintext, KeyType.PublicKey, Charset.forName("utf-8"));
        return dencryptStr;
    }
}
