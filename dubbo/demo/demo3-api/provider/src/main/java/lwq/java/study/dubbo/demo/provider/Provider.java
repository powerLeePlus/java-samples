package lwq.java.study.dubbo.demo.provider;

import java.io.IOException;

/**
 * @author lwq
 * @date 2019-09-11 下午 4:56
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        ProviderAPI.export();
        System.out.println("按任意键退出");
        System.in.read();
    }
}
