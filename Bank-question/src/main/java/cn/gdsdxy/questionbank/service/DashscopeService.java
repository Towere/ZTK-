package cn.gdsdxy.questionbank.service;

import cn.gdsdxy.questionbank.config.DashscopeConfig;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashscopeService {

    private final DashscopeConfig dashscopeConfig;

    public String getAnswer(String question) {
        // 验证 API Key
        if (dashscopeConfig.getApiKey() == null || dashscopeConfig.getApiKey().isEmpty()) {
            throw new RuntimeException("未配置 Dashscope API 密钥");
        }

        // 构建请求体
        JSONObject requestBody = buildRequestBody(question);

        try {
            // 发送 POST 请求
            String response = HttpRequest.post(dashscopeConfig.getApiUrl())
                    .header("Authorization", "Bearer " + dashscopeConfig.getApiKey())
                    .header("Content-Type", "application/json")
                    .body(requestBody.toString())
                    .execute()
                    .body();

            log.info("Dashscope 响应: {}", response);

            // 解析响应结果
            return parseResponse(response);
        } catch (Exception e) {
            log.error("调用 Dashscope API 失败", e);
            throw new RuntimeException("获取 AI 回答失败: " + e.getMessage());
        }
    }

    private JSONObject buildRequestBody(String question) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", dashscopeConfig.getModel());

        // 构建输入消息
        JSONObject input = new JSONObject();
        JSONArray messages = new JSONArray();

        // 系统提示
        JSONObject systemMessage = new JSONObject();
        systemMessage.put("role", "system");
        systemMessage.put("content", dashscopeConfig.getSystemPrompt());
        messages.add(systemMessage);

        // 用户提问
        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", question);
        messages.add(userMessage);

        input.put("messages", messages);
        requestBody.put("input", input);

        // 请求参数
        JSONObject parameters = new JSONObject();
        parameters.put("result_format", "message");
        requestBody.put("parameters", parameters);

        return requestBody;
    }

    private String parseResponse(String response) {
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("API 返回空响应");
        }

        JSONObject jsonResponse = JSONUtil.parseObj(response);

        // 检查是否有错误
        if (jsonResponse.containsKey("error")) {
            JSONObject error = jsonResponse.getJSONObject("error");
            throw new RuntimeException("API 错误: " + error.getStr("message"));
        }

        // 检查 output 是否存在
        if (!jsonResponse.containsKey("output")) {
            throw new RuntimeException("API 响应缺少 output 字段");
        }

        JSONObject output = jsonResponse.getJSONObject("output");
        if (output == null) {
            throw new RuntimeException("API 响应的 output 字段为空");
        }

        // 检查 choices 是否存在
        if (!output.containsKey("choices")) {
            throw new RuntimeException("API 响应缺少 choices 字段");
        }

        JSONArray choices = output.getJSONArray("choices");
        if (choices == null || choices.isEmpty()) {
            throw new RuntimeException("未获取到回答内容");
        }

        JSONObject firstChoice = choices.getJSONObject(0);
        if (firstChoice == null) {
            throw new RuntimeException("未获取到回答内容");
        }

        // 检查 message 是否存在
        if (!firstChoice.containsKey("message")) {
            throw new RuntimeException("API 响应缺少 message 字段");
        }

        JSONObject message = firstChoice.getJSONObject("message");
        if (message == null) {
            throw new RuntimeException("API 响应的 message 字段为空");
        }

        // 检查 content 是否存在
        if (!message.containsKey("content")) {
            throw new RuntimeException("API 响应缺少 content 字段");
        }

        return message.getStr("content");
    }
}