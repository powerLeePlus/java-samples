package lwq.java.web.ssm_swagger.util;

import java.util.UUID;

/**
 * @ Created by liwenqiang on 2017/4/27 0027.
 * @ Description:
 */
public class UUIDUtil {
    public static void main(String[] args) {
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString().replaceAll("-", "");//用来生成数据库的主键id非常不错。。
        System.out.println(s);
    }

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString().replaceAll("-", "");//用来生成数据库的主键id非常不错。。
        return s;
    }

}
