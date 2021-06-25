package com.lq.mytool.common;

import com.lq.mytool.enums.MsgCodeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseResponse<T> {
    /**
     * 状态码
     * 0成功，1失败
     */
    @ApiModelProperty(value = "0-成功，1-失败")
    private int code;
    /**
     * 描述信息
     * 成功，或具体错误信息
     */
    @ApiModelProperty(value = "描述信息")
    private String msg;
    /**
     * 业务数据
     */
    @ApiModelProperty(value = "数据体")
    private T data;

    public BaseResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> BaseResponse<T> fail(String msg) {
        return new BaseResponse<T>(MsgCodeEnum.FAILED.getCode(), msg, null);
    }

    public static <T> BaseResponse<T> fail(MsgCodeEnum msgCodeEnum) {
        return new BaseResponse<T>(msgCodeEnum.getCode(), msgCodeEnum.getMsg(), null);
    }

    public static <T> BaseResponse<T> fail(Integer code, String msg) {
        return new BaseResponse<T>(code, msg, null);
    }


    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<T>(MsgCodeEnum.SUCCESS.getCode(), MsgCodeEnum.SUCCESS.getMsg(), data);
    }

    public static <T> BaseResponse<T> fail() {
        return new BaseResponse<T>(MsgCodeEnum.FAILED.getCode(), MsgCodeEnum.FAILED.getMsg(), null);
    }

    public static <T> BaseResponse<T> success() {
        return new BaseResponse<T>(MsgCodeEnum.SUCCESS.getCode(), MsgCodeEnum.SUCCESS.getMsg(), null);
    }

    public static <T> BaseResponse<T> success(MsgCodeEnum msgCodeEnum) {
        return new BaseResponse<T>(msgCodeEnum.getCode(), msgCodeEnum.getMsg(), null);
    }

}
