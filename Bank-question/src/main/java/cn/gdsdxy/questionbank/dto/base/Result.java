package cn.gdsdxy.questionbank.dto.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 功能描述：
 */
@ApiModel("统一返回Dto")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class Result<T> implements Serializable {

    @ApiModelProperty("响应码")
    private HttpStatus code;

    @ApiModelProperty("描述")
    private String message;

    @ApiModelProperty("数据")
    private T data;

    public Result ok() {
        return new Result(HttpStatus.OK, "操作成功", null);
    }

    public Result ok(String message) {
        return new Result(HttpStatus.OK, message, null);
    }

    public Result ok(T data) {
        return new Result(HttpStatus.OK, "", data);
    }

    public Result ok(String message, T data) {
        return new Result(HttpStatus.OK, message, data);
    }

    public Result serverError() {
        return new Result(HttpStatus.INTERNAL_SERVER_ERROR, "服务器异常", null);
    }

    public Result serverError(String message) {
        return new Result(HttpStatus.INTERNAL_SERVER_ERROR, message, null);
    }

    public Result serverError(T data) {
        return new Result(HttpStatus.INTERNAL_SERVER_ERROR, "", data);
    }

    public Result serverError(String message, T data) {
        return new Result(HttpStatus.INTERNAL_SERVER_ERROR, message, data);
    }

}
