package lwq.java.web.ssm_swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 配置swagger2
 * @author lwq
 * @create 2019-07-09 下午 4:54
 */

//swagger交由spring管理
@Configuration
//使swagger生效
@EnableSwagger2
//@EnableWebMvc
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                //设置只生成被@Api注解的Controller类中有@ApiOperation注解的api接口的文档
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class)).apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //.apis(RequestHandlerSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Springfox-swagger2 API")
                //.description("Springfox-swagger2 API在线文档")
                .version("1.0.0")
                //.termsOfServiceUrl("http://www.lwqgj.cn")
                //.license("")
                //.licenseUrl("")
                .build();
    }
    /*
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("车联智控开放平台API")
                //.description("车联智控开放平台API接口文档")
                .version("1.0.0")
                //.termsOfServiceUrl("http://www.huatec.com")
                //.license("华晟经世")
                //.licenseUrl("http://www.huatec.com")
                .build();
    }
    */
}