package com.example.es.demo;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;

/**
 * 自定义配置 es client连接，使用这个配置，就不需要在application.properties中配置es相关配置项了。二选其一
 * @author lwq
 * @date 2020/3/27 0027
 */
// @Configuration
public class Config {

    /**
     * 默认transport client端口9300, rest client端口9200
     * */

    /**
     * rest client 方式， 包括 restClient 和 restHighLevelClient
     * */
    @Bean(value = "restHighLevelClient")
    public RestHighLevelClient restHighLevelClient(RestClientBuilder restClientBuilder) {
        return new RestHighLevelClient(restClientBuilder);
    }

    @Bean(value = "restClient")
    public RestClient restClient(RestClientBuilder restClientBuilder) {
        return restClientBuilder.build();
    }

    @Bean("restClientBuilder")
    public RestClientBuilder restClientBuilder(){

        HttpHost httpHost = new HttpHost("127.0.0.1", 9200);
        RestClientBuilder builder = RestClient.builder(httpHost);

        // 配置用户名密码
        final BasicCredentialsProvider basicCredentialsProvider = new BasicCredentialsProvider();
        basicCredentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "elastic"));
        builder.setHttpClientConfigCallback( httpAsyncClientBuilder -> {
            httpAsyncClientBuilder.setDefaultCredentialsProvider(basicCredentialsProvider);
            return httpAsyncClientBuilder;
        });
        /*builder.setRequestConfigCallback(requestConfigBuilder -> {
            //
            // requestConfigBuilder.setConnectTimeout(-1);
            // requestConfigBuilder.setSocketTimeout(-1);
            // requestConfigBuilder.setConnectionRequestTimeout(-1);
            return requestConfigBuilder;
        });*/

        /*
        // spring-data-es 封装的
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("localhost:9200", "localhost:9291")
                .withConnectTimeout(Duration.ofSeconds(5))
                .withSocketTimeout(Duration.ofSeconds(3))
                .useSsl()
                .withDefaultHeaders(defaultHeaders)
                .withBasicAuth(username, password)
                . // ... other options
        .build();*/

       return builder;
    }

    /**
     * transport client 方式 ,
     * 不加 ssl + 用户名密码认证
     * */
    /*@Bean(name = "transportClient")
    public TransportClient transportClient() throws UnknownHostException {

        TransportClient transportClient = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        *//*Settings esSetting = Settings.builder().put("cluster.name", "my-application") // 集群名字
                // .put("client.transport.sniff", true)// 增加嗅探机制，找到ES集群
                // .put("thread_pool.search.size", 5)// 增加线程池 threadpool search个数
                .build();
        TransportClient transportClient = new PreBuiltTransportClient(esSetting);*//*

        return transportClient;
    }*/

    /**
     * transport client方式
     * 加 ssl + 用户名密码认证，用户名密码认证和ssl认证可以分别单独开启，也可以一起启用
     */
    // @Bean
    // public TransportClient transportClient() throws UnknownHostException {
    //     // 配置信息
    //     Settings esSetting = Settings.builder()
    //             // .put("cluster.name", "my-application") // 集群名字
    //             /* 启用用户名密码  --->  对应配置elasticsearch配置文件elasticsearch.yml中的xpack.security.enabled: true
    //              * 可以单独开启，而不开启ssl认证
    //              */
    //             .put("xpack.security.user", "elastic:elastic")
    //             /* 启动ssl认证  --->  对应配置elasticsearch配置文件elasticsearch.yml中的 xpack.security.transport.ssl.enabled: true
    //              * 与以下xpack.security.transport.ssl.keystore.path xpack.security.transport.ssl.truststore.path xpack
    //              * .security.transport.ssl.verification_mode一起使用
    //              */
    //             // .put("xpack.security.transport.ssl.enabled", true)
    //             // ssl认证方式  --->  对应配置elasticsearch配置文件elasticsearch.yml中的 xpack.security.transport.ssl.verification_mode: certificate
    //             // .put("xpack.security.transport.ssl.verification_mode", "certificate")
    //             // ssl证书路径  --->  对应配置elasticsearch配置文件elasticsearch.yml中的 xpack.security.transport.ssl.keystore.path: elastic-certificates.p12
    //             // .put("xpack.security.transport.ssl.keystore.path", "")
    //             // ssl证书路径  --->  对应配置elasticsearch配置文件elasticsearch.yml中的 xpack.security.transport.ssl.truststore.path: elastic-certificates.p12
    //             // .put("xpack.security.transport.ssl.truststore.path", "")
    //             // .put("client.transport.sniff", true)// 增加嗅探机制，找到ES集群
    //             // .put("thread_pool.search.size", 5)// 增加线程池 threadpool search个数
    //             .build();
    //     TransportClient transportClient = new PreBuiltXPackTransportClient(esSetting);
    //
    //     // 配置连接地址
    //     // 单机
    //     transportClient.addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300)); //
    //     // 集群
    //     // TransportAddress address1 = new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300);
    //     // TransportAddress address2 = new TransportAddress(InetAddress.getByName("172.16.20.220"), 9300);
    //     // transportClient.addTransportAddresses(address1, address2);
    //
    //     return transportClient;
    //
    // }

}
