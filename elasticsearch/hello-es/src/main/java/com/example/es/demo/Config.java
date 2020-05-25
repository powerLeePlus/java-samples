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
 * @author lwq
 * @date 2020/3/27 0027
 */
//@Configuration
public class Config {

    /**
     * transport client 方式
     * */
    /*@Bean(name = "transportClient")
    public TransportClient transportClient() throws UnknownHostException {

        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        return client;
    }*/

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
        final BasicCredentialsProvider basicCredentialsProvider = new BasicCredentialsProvider();
        basicCredentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "elastic"));

        HttpHost httpHost = new HttpHost("127.0.0.1", 9200);
        RestClientBuilder builder = RestClient.builder(httpHost);
       /* builder.setHttpClientConfigCallback( httpAsyncClientBuilder -> {
            httpAsyncClientBuilder.setDefaultCredentialsProvider(basicCredentialsProvider);
            return httpAsyncClientBuilder;
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


}
