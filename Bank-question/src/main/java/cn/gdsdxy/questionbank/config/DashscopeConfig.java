package cn.gdsdxy.questionbank.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "dashscope")
public class DashscopeConfig {
    /**
     * API密钥
     */
    private String apiKey;

    /**
     * 接口地址
     */
    private String apiUrl = "https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation";

    /**
     * 模型名称
     */
    private String model = "qwen-plus";

    /**
     * 系统提示词
     */
    private String systemPrompt = "You are a helpful assistant.";
}