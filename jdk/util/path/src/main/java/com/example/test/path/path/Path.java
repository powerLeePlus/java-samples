package com.example.test.path.path;

import java.io.*;

import org.springframework.util.ClassUtils;


/**
 * @author lwq
 * @date 2020/4/1 0001
 */
public class Path {

    public static void main(String[] args) throws IOException {
        testPath();
        checkZZhonwenPath();
        zhongwenPath();
        applicationHome();
        test();
    }

    public static void testPath() throws IOException {
        String path = System.getProperty("user.dir");
        String path0 = System.getProperty("user.home");
        File file = new File("");
        String path1 = file.getPath();
        File file1 = new File("/");
        String path2 = file1.getPath();
        String absolutePath = file.getAbsolutePath();
        String canonicalPath = file.getCanonicalPath();
        System.out.println("user.home  \t === " +  path0);
        System.out.println("user.dir  \t === " +  path);
        System.out.println("getPath(\"\") \t === " + path1);
        System.out.println("getPath(\"/\") \t === " + path2);
        System.out.println("getAbsolutePath \t === " + absolutePath);
        System.out.println("getCanonicalPath \t === " + canonicalPath);

        System.out.println("------------------------------------------");

        String r1 = Path.class.getResource("").getPath();
        String r2 = Path.class.getResource("/").getPath();

        System.out.println("class.getResource(\"\") \t === " + r1);
        System.out.println("class.getResource(\"/\") \t === " + r2);

        Path initBootElasticsearchParams = new Path();
        String r3 = initBootElasticsearchParams.getClass().getResource("").getPath();
        String r4 = initBootElasticsearchParams.getClass().getResource("/").getPath();

        System.out.println("getClass.getResource(\"\") \t === " + r3);
        System.out.println("getClass.getResource(\"/\") \t === " + r4);

        String r5 = initBootElasticsearchParams.getClass().getClassLoader().getResource("").getPath();
        //String r6 = initBootElasticsearchParams.getClass().getClassLoader().getResource("/").getPath();  //
        // getResource("/") == null
        System.out.println("classLoader.getResource(\"\") \t === " + r5);
        //System.out.println("classLoader.getResource(\"/\") === " + r6);

        System.out.println("------------------------------------------");

        File file3 = new File("..");
        String path3 = file3.getPath();
        String absolutePath3 = file3.getAbsolutePath();
        String canonicalPath4 = file3.getCanonicalPath();
        System.out.println("getPath(\".\") \t === " + path2);
        System.out.println("getAbsolutePath(.) \t === " + absolutePath3);
        System.out.println("getCanonicalPath(.) \t === " + canonicalPath4);

        System.out.println("------------------------------------------");
        String property = System.getProperty("java.class.path");
        System.out.println(property);

        System.out.println("------------------------------------------");
        // spring API
        ApplicationHome home = new ApplicationHome();
        // ApplicationHome home = new ApplicationHome(Path.class);
        File homeDir = home.getDir();
        String rootPath = homeDir.toString();
        System.out.println("spring ApplicationHome without param \t === " + rootPath);

        ApplicationHome home2 = new ApplicationHome(Path.class);
        File homeDir2 = home2.getDir();
        String rootPath2 = homeDir2.toString();
        System.out.println("spring ApplicationHome with param \t === " + rootPath2);

        // request  ServletContext 待补
    }

    public static void getResource() throws IOException {
        InputStream fd = ClassUtils.getDefaultClassLoader().getResourceAsStream("application.properties");
        BufferedReader br = new BufferedReader(new InputStreamReader(fd));
        StringBuffer sb = new StringBuffer();
        String readLine;
        while ((readLine = br.readLine()) != null) {
            if (readLine.charAt(0) == '-') {
                continue;
            } else {
                sb.append(readLine + "");
            }
        }

        System.out.println(sb.toString());

    }

    public static void checkZZhonwenPath(){
        File file = new File("D:\\maven\\仓库\\repos");
        boolean exists = file.exists();
        System.out.println("含中文的路径：file.exists()？" + exists);
    }

    public static void zhongwenPath(){
        //关键是这行...
        String path = Path.class.getProtectionDomain().getCodeSource()
                .getLocation().getFile();
        try{
            path = java.net.URLDecoder.decode(path, "UTF-8");//转换处理中文及空格
        }catch (java.io.UnsupportedEncodingException e){
           e.printStackTrace();
        }
    }

    public static void applicationHome(){
        com.example.test.path.path.ApplicationHome applicationHome =
                new com.example.test.path.path.ApplicationHome(Path.class);
        File homeDir = applicationHome.getDir();
        System.out.println(homeDir);
    }

    public static void test(){
        File file = new File("D:\\maven\\仓库\\repos\\webapps\\path\\WEB-INF\\lib");
        String dir = file.toString();
        String libMarker = "WEB-INF" + File.separator + "lib";
        if(dir.contains(libMarker)) {
            // tomcat war
            dir = dir.substring(0, dir.indexOf(libMarker) - 1);
        }
        System.out.println(dir);
    }
}
