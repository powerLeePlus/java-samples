package lwq.java.web.ssm_swagger.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密算法
 */
public class MD5Util {

    public static void main(String[] args) {
        System.out.println(MD5Util.getMD5Str("qwe123"));
        //4c56ff4ce4aaf9573aa5dff913df997a
        //88c8582ab98eb1fd7b5fea1de9df6194
        //0ff8acd155579cdb0f3b878d659bab04
        //  损耗型加密
    }

    /**
     * MD5 加密
     */
    public static String getMD5Str(String str) {

        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();

            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            System.exit(-1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        return md5StrBuff.toString();
    }
}
