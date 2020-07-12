import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @author lwq
 * @date 2020/7/12 0012
 * @since
 */
public class CryptographyTest {

    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123", "bljOhDqx", 1024);
        System.out.println(md5Hash);
    }
}
