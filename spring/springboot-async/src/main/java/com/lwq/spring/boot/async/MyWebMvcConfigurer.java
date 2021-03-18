package com.lwq.spring.boot.async;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Spring MVC 配置
 */
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
    private final Logger logger = LoggerFactory.getLogger(MyWebMvcConfigurer.class);

    // 异步处理的
    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        // 指定异步执行时的线程池
        configurer.setTaskExecutor(springTaskExecutor());
        // 指定异步执行的超时时间。不指定使用servlet3实现容器的默认值，如tomcat：10s
        // configurer.setDefaultTimeout(30000L);
    }

    /**
     * 自定义异步线程池
     * @return
     */
    @Bean
    public ThreadPoolTaskExecutor springTaskExecutor() {
        ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
        executor.setQueueCapacity(10000);
        executor.setMaxPoolSize(300);
        executor.setCorePoolSize(20);
        executor.setKeepAliveSeconds(30);
        executor.setThreadNamePrefix("SpringThread-");
        return executor;
    }
}
