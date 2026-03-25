package cn.gdsdxy.questionbank.config;



import cn.gdsdxy.questionbank.constant.JwtConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration // 配置类
@EnableSwagger2 // 开启 swagger2 的自动配置
public class SwaggerConfig {
    @Bean
    public Docket docket(Environment environment) {
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new ParameterBuilder()
                .name(JwtConstant.HEADER_NAME) // 字段名
                .description("token信息") // 描述
                .modelRef(new ModelRef("string")) // 数据类型
                .parameterType("header") // 参数类型
                .defaultValue("default value") // 默认值：可自己设置
                .hidden(true) // 是否隐藏
                .required(false) // 是否必须
                .build());


        Profiles profiles = Profiles.of("dev","test");
        //设置环境范围

        //如果在测试环境返回内侧返回：true 反之返回 false
        boolean flag = environment.acceptsProfiles(profiles);
        // 创建一个 swagger 的 bean 实例
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                // 配置基本信息
                .enable(flag)//flag的值将决定是否显示swagger
                .groupName("dev")
                .globalOperationParameters(parameters)
                .select()//设置扫描接口

                //配置如何扫描接口
                .apis(RequestHandlerSelectors
//                        .any()//提供全部接口（默认）
//                        .none()//不提供任何接口
//                        .basePackage("cn.gdsdxy.shop.controller")//扫描带有指定包路径的接口，最常用\
                        .withClassAnnotation(Controller.class)//扫描指定注解的接口
//                        .withMethodAnnotation(PostMapping.class)//扫描指定注解的方法接口



                )
                .paths(PathSelectors
                        .any()//满足条件的路径
                        //.none()//不满足条件的路径
                        //.ant("/shop/**/")//满足字符串表达式路径
                        //.regex("")//符合正确的路径

                ).build();
    }
    @Bean
    public Docket docket2(Environment environment) {
        Profiles profiles = Profiles.of("dev","test");
        //设置环境范围

        //如果在测试环境返回内侧返回：true 反之返回 false
        boolean flag = environment.acceptsProfiles(profiles);
        // 创建一个 swagger 的 bean 实例
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("test")
                // 配置基本信息
                .enable(flag)//flag的值将决定是否显示swagger
                .select()//设置扫描接口

                //配置如何扫描接口

                .apis(RequestHandlerSelectors
//                        .any()//提供全部接口（默认）
//                        .none()//不提供任何接口
//                        .basePackage("cn.gdsdxy.shop.controller")//扫描带有指定包路径的接口，最常用\
                                .withClassAnnotation(RestController.class)//扫描指定注解的接口
//                        .withMethodAnnotation(PostMapping.class)//扫描指定注解的方法接口



                )
                .paths(PathSelectors
                                .any()//满足条件的路径
                        //.onne//不满足条件的路径
                        //.ant("/shop/**/")//满足字符串表达式路径
                        //.regex("")//符合正确的路径

                ).build()
                ;
    }

    // 基本信息设置
    private ApiInfo apiInfo() {
        Contact contact = new Contact(
                "米大傻", // 作者姓名
                "https://blog.csdn.net/xhmico?type=blog", // 作者网址
                "777777777@163.com"); // 作者邮箱
        return new ApiInfoBuilder()
                .title("多加辣-接口文档") // 标题
                .description("众里寻他千百度，慕然回首那人却在灯火阑珊处") // 描述
                .termsOfServiceUrl("https://www.baidu.com") // 跳转连接
                .version("1.0") // 版本
                .license("Swagger-的使用(详细教程)")
                .licenseUrl("https://blog.csdn.net/xhmico/article/details/125353535")
                .contact(contact)
                .build();
    }

}
