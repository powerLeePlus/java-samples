package lwq.java.study.dubbo.demo;

import java.util.Properties;

/**
 * @author lwq
 * @date 2019-09-10 下午 4:24
 */
public class Main {
    public static void main(String[] args) {
        Properties properties = System.getProperties();
        for (Object key : properties.keySet()) {
            System.out.println(key + "=" + properties.getProperty((String) key));
        }
    }
}
